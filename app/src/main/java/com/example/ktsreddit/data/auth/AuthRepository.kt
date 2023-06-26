package com.kts.github.data.auth

import com.example.ktsreddit.data.storage.shared.KeyValueStorage
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import timber.log.Timber

class AuthRepository {
    private val sharedMem = KeyValueStorage

    fun getAuthRequest(): AuthorizationRequest {
        return AppAuth.getAuthRequest()
    }

    suspend fun performTokenRequest(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
    ) {
        val tokens = AppAuth.performTokenRequestSuspend(authService, tokenRequest)
        //обмен кода на токен произошел успешно, сохраняем токены и завершаем авторизацию
        sharedMem.setAuthToken(tokens.accessToken)
        sharedMem.setRefreshToken(tokens.refreshToken)

        Timber.tag("Oauth").d("6. Tokens accepted:\n access=${tokens.accessToken}")
    }

}