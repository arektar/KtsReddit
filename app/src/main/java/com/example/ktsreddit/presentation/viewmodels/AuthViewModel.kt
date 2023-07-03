package com.example.ktsreddit.presentation.viewmodels

import android.app.Application
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.ktsreddit.BuildConfig
import com.example.ktsreddit.R
import com.example.ktsreddit.data.auth.models.*
import com.example.ktsreddit.data.storage.shared.KeyValueStorage
import com.example.ktsreddit.presentation.common.navigation.NawRoute
import com.example.ktsreddit.presentation.common.utils.OneTimeEvent
import com.kts.github.data.auth.AuthRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import org.koin.core.component.KoinComponent
import timber.log.Timber

class AuthViewModel(application: Application, private val savedStateHandle: SavedStateHandle) :
    AndroidViewModel(application), KoinComponent {

    private val _authState = MutableStateFlow(DEFAULT_AUTH_STATE)
    val authState: StateFlow<UIAuthState> = _authState.asStateFlow()

    private val mutableNavEvent = OneTimeEvent<NawRoute>()
    val navEvents: Flow<NawRoute>
        get() = mutableNavEvent.receiveAsFlow()


    private val authRepository = AuthRepository()
    private val authService: AuthorizationService = AuthorizationService(getApplication())

    val authEvents: Flow<AuthEvent> = savedStateHandle.getStateFlow(
        AUTH_EVENTS_SAVE_KEY,
        DEFAULT_AUTH_EVENTS
    )


    private fun sendAuthEvent(event: AuthEvent) {
        savedStateHandle[AUTH_EVENTS_SAVE_KEY] = event
    }


    fun onAuthCodeFailed() {
        sendAuthEvent(AuthToast(R.string.auth_failed))
    }

    fun onAuthCodeReceived(tokenRequest: TokenRequest) {

        Timber.tag("Oauth").d("3. Received code = ${tokenRequest.authorizationCode}")

        viewModelScope.launch {
            _authState.value = _authState.value.copy(loading = true)
            //setAuthState(newState)
            runCatching {
                Timber.tag("Oauth")
                    .d("4. Change code to token. Url = ${tokenRequest.configuration.tokenEndpoint}, verifier = ${tokenRequest.codeVerifier}")
                authRepository.performTokenRequest(
                    authService = authService,
                    tokenRequest = tokenRequest
                )
            }.onSuccess {
                _authState.value = _authState.value.copy(loading = false)
                sendAuthEvent(AuthSuccess)

            }.onFailure {
                _authState.value = _authState.value.copy(loading = false)
                sendAuthEvent(AuthToast(R.string.auth_failed))


            }
        }
    }

    fun openLoginPage() {

        val customTabsIntent = CustomTabsIntent.Builder().build()

        val authRequest = authRepository.getAuthRequest()

        Timber.tag("Oauth")
            .d("1. Generated verifier=${authRequest.codeVerifier},challenge=${authRequest.codeVerifierChallenge}")

        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            authRequest,
            customTabsIntent
        )

        //openAuthPageEventChannel.trySendBlocking(openAuthPageIntent)
        sendAuthEvent(AuthIntent(openAuthPageIntent))
        Timber.tag("Oauth").d("2. Open auth page: ${authRequest.toUri()}")

    }

    private fun skipForAuthorized() {
        if (KeyValueStorage.getAuthToken() != null) {
            navigateNext()
        }
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }

    fun navigateNext() {
        mutableNavEvent.trySend(NawRoute.Main)
    }


    companion object {
        val DEFAULT_AUTH_STATE = UIAuthState()
        private const val AUTH_EVENTS_SAVE_KEY = "AUTH_EVENTS_SAVE_KEY"
        val DEFAULT_AUTH_EVENTS = AuthDefault
    }

    fun handleAuthResponseIntent(
        intent: Intent
    ) {
        // пытаемся получить ошибку из ответа. null - если все ок
        val exception = AuthorizationException.fromIntent(intent)
        // пытаемся получить запрос для обмена кода на токен, null - если произошла ошибка
        val tokenExchangeRequest = AuthorizationResponse.fromIntent(intent)
            ?.createTokenExchangeRequest()
        when {
            // авторизация завершались ошибкой
            exception != null -> onAuthCodeFailed()
            // авторизация прошла успешно, меняем код на токен
            tokenExchangeRequest != null ->
                onAuthCodeReceived(tokenExchangeRequest)
        }
    }

    init {
        skipForAuthorized()
    }

}

data class UIAuthState(
    val login: String = "",
    val password: String = "",
    val loginIsCorrect: Boolean = false,
    val passwordIsCorrect: Boolean = false,

    val loading: Boolean = false
) {
    // TODO: Fix at prod
    val loginEnabled = if (BuildConfig.DEBUG) {
        true
    } else {
        this.loginIsCorrect && this.passwordIsCorrect
    }
}