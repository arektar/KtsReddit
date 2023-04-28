package com.example.ktsreddit.presentation.auth

import android.app.Application
import android.content.Intent
import android.util.Patterns
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktsreddit.BuildConfig
import com.example.ktsreddit.R
import com.kts.github.data.auth.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import timber.log.Timber

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val _authState = MutableStateFlow(DEFAULT_AUTH_STATE)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val authRepository = AuthRepository()
    private val authService: AuthorizationService = AuthorizationService(getApplication())

    private val openAuthPageEventChannel = Channel<Intent>(Channel.BUFFERED)
    private val toastEventChannel = Channel<Int>(Channel.BUFFERED)
    private val authSuccessEventChannel = Channel<Unit>(Channel.BUFFERED)

    private val loadingMutableStateFlow = MutableStateFlow(false)

    val openAuthPageFlow: Flow<Intent>
        get() = openAuthPageEventChannel.receiveAsFlow()

    val loadingFlow: Flow<Boolean>
        get() = loadingMutableStateFlow.asStateFlow()

    val toastFlow: Flow<Int>
        get() = toastEventChannel.receiveAsFlow()

    val authSuccessFlow: Flow<Unit>
        get() = authSuccessEventChannel.receiveAsFlow()



    fun validateLogin(login: String) {
        val loginIsCorrect = Patterns.EMAIL_ADDRESS.matcher(login).matches()
        val newAuthState = _authState.value.copy(
            login = login,
            loginIsCorrect = loginIsCorrect
        )
        _authState.value = newAuthState
    }

    fun validatePassword(password: String) {
        val passwordIsCorrect = password.length >= 8
        val newAuthState = _authState.value.copy(
            password = password,
            passwordIsCorrect = passwordIsCorrect
        )
        _authState.value = newAuthState
    }

    fun onAuthCodeFailed(exception: AuthorizationException) {
        toastEventChannel.trySendBlocking(R.string.auth_failed)
    }

    fun onAuthCodeReceived(tokenRequest: TokenRequest) {

        Timber.tag("Oauth").d("3. Received code = ${tokenRequest.authorizationCode}")

        viewModelScope.launch {
            loadingMutableStateFlow.value = true
            runCatching {
                Timber.tag("Oauth").d("4. Change code to token. Url = ${tokenRequest.configuration.tokenEndpoint}, verifier = ${tokenRequest.codeVerifier}")
                authRepository.performTokenRequest(
                    authService = authService,
                    tokenRequest = tokenRequest
                )
            }.onSuccess {
                loadingMutableStateFlow.value = false
                authSuccessEventChannel.send(Unit)
            }.onFailure {
                loadingMutableStateFlow.value = false
                toastEventChannel.send(R.string.auth_failed)
            }
        }
    }

    fun openLoginPage() {
        val customTabsIntent = CustomTabsIntent.Builder().build()

        val authRequest = authRepository.getAuthRequest()

        Timber.tag("Oauth").d("1. Generated verifier=${authRequest.codeVerifier},challenge=${authRequest.codeVerifierChallenge}")

        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            authRequest,
            customTabsIntent
        )

        openAuthPageEventChannel.trySendBlocking(openAuthPageIntent)
        Timber.tag("Oauth").d("2. Open auth page: ${authRequest.toUri()}")
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }


    companion object {
        val DEFAULT_AUTH_STATE = AuthState()
    }

}

data class AuthState(
    val login: String = "",
    val password: String = "",
    val loginIsCorrect: Boolean = false,
    val passwordIsCorrect: Boolean = false,
) {
    // TODO: Fix at prod
    val loginEnabled = if (BuildConfig.DEBUG) {
        true
    } else {
        this.loginIsCorrect && this.passwordIsCorrect
    }
}