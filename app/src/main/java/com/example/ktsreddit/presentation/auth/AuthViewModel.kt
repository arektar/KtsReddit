package com.example.ktsreddit.presentation.auth

import android.app.Application
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.ktsreddit.BuildConfig
import com.example.ktsreddit.R
import com.example.ktsreddit.data.auth.models.*
import com.kts.github.data.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import timber.log.Timber

class AuthViewModel(application: Application, private val savedStateHandle: SavedStateHandle) :
    AndroidViewModel(application) {

    private val _authState = MutableStateFlow(DEFAULT_AUTH_STATE)
    val authState: StateFlow<UIAuthState> = _authState.asStateFlow()


    private val authRepository = AuthRepository()
    private val authService: AuthorizationService = AuthorizationService(getApplication())

    val openAuthEventsFlow: Flow<AuthEvent> = savedStateHandle.getStateFlow(
        AUTH_EVENTS_SAVE_KEY,
        DEFAULT_AUTH_EVENTS
    )


    private fun sendAuthEvent(event: AuthEvent) {
        savedStateHandle[AUTH_EVENTS_SAVE_KEY] = event
    }


    fun onAuthCodeFailed(exception: AuthorizationException) {
        sendAuthEvent(AuthToast(R.string.auth_failed))
    }

    fun onAuthCodeReceived(tokenRequest: TokenRequest) {

        Timber.tag("Oauth").d("3. Received code = ${tokenRequest.authorizationCode}")

        viewModelScope.launch {
            _authState.value.loadingMutableStateFlow.value = true
            //setAuthState(newState)
            runCatching {
                Timber.tag("Oauth")
                    .d("4. Change code to token. Url = ${tokenRequest.configuration.tokenEndpoint}, verifier = ${tokenRequest.codeVerifier}")
                authRepository.performTokenRequest(
                    authService = authService,
                    tokenRequest = tokenRequest
                )
            }.onSuccess {
                _authState.value.loadingMutableStateFlow.value = false
                sendAuthEvent(AuthSuccess)

            }.onFailure {
                _authState.value.loadingMutableStateFlow.value = false
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

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }


    companion object {
        val DEFAULT_AUTH_STATE = UIAuthState()
        private const val AUTH_EVENTS_SAVE_KEY = "AUTH_EVENTS_SAVE_KEY"
        val DEFAULT_AUTH_EVENTS = AuthDefault
    }

}

data class UIAuthState(
    val login: String = "",
    val password: String = "",
    val loginIsCorrect: Boolean = false,
    val passwordIsCorrect: Boolean = false,

    val loadingMutableStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false),
    val loadingFlow: Flow<Boolean> = loadingMutableStateFlow.asStateFlow()
) {
    // TODO: Fix at prod
    val loginEnabled = if (BuildConfig.DEBUG) {
        true
    } else {
        this.loginIsCorrect && this.passwordIsCorrect
    }
}