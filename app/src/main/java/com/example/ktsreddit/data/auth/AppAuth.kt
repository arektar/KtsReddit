package com.kts.github.data.auth

import android.net.Uri
import com.kts.github.data.auth.AppAuth.AuthConfig.CLIENT_SECRET
import com.example.ktsreddit.data.auth.models.TokensModel
import net.openid.appauth.*
import kotlin.coroutines.suspendCoroutine

object AppAuth {

    private val serviceConfiguration = AuthorizationServiceConfiguration(
        Uri.parse(AuthConfig.AUTH_URI),
        Uri.parse(AuthConfig.TOKEN_URI)
    )

    fun getAuthRequest(): AuthorizationRequest {
        val redirectUri = Uri.parse(AuthConfig.CALLBACK_URL)


        return AuthorizationRequest.Builder(
            serviceConfiguration,
            AuthConfig.CLIENT_ID,
            AuthConfig.RESPONSE_TYPE,
            redirectUri
        )
            .setScope(AuthConfig.SCOPE)
            .build()
    }

    fun getRefreshTokenRequest(refreshToken: String): TokenRequest {
        return TokenRequest.Builder(
            serviceConfiguration,
            AuthConfig.CLIENT_ID
        )
            .setGrantType(GrantTypeValues.REFRESH_TOKEN)
            .setScopes(AuthConfig.SCOPE)
            .setRefreshToken(refreshToken)
            .build()
    }

    suspend fun performTokenRequestSuspend(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
    ): TokensModel {
        return suspendCoroutine { continuation ->
            authService.performTokenRequest(
                tokenRequest,
                getClientAuthentication()
            ) { response, ex ->
                when {
                    response != null -> {
                        //получение токена произошло успешно
                        val tokens = TokensModel(
                            accessToken = response.accessToken.orEmpty(),
                            refreshToken = response.refreshToken.orEmpty(),
                            idToken = response.idToken.orEmpty()
                        )
                        continuation.resumeWith(Result.success(tokens))

                    }
                    //получение токенов произошло неуспешно, показываем ошибку
                    ex != null -> {
                        continuation.resumeWith(Result.failure(ex))
                    }
                    else -> error("unreachable")
                }
            }
        }
    }

    //suspend fun performModHashRequestSuspend

    private fun getClientAuthentication(): ClientAuthentication {
        return ClientSecretBasic(CLIENT_SECRET)
    }

    private object AuthConfig {
        const val AUTH_URI = "https://ssl.reddit.com/api/v1/authorize.compact"
        const val TOKEN_URI = "https://ssl.reddit.com/api/v1/access_token"
        const val RESPONSE_TYPE = ResponseTypeValues.CODE
        const val SCOPE = "vote read identity save"

        const val CLIENT_ID = "GcNyb2zCGbTElwU78yCiXA"
        const val CLIENT_SECRET = ""
        const val CALLBACK_URL = "com.arektar://oauth2redirect/reddit"
    }
}