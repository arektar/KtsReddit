package com.example.ktsreddit.data.network

import com.kts.github.data.auth.TokenStorage
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

class OkHttpClientOauth {

    fun getClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder()

            if (TokenStorage.accessToken != null) {
                builder
                    .addHeader(AUTH_HEADER, AUTH_VALUE)
                    .addHeader(USER_AGENT_HEADER, USER_AGENT_VALUE)
            }

            val request = builder.method(original.method, original.body)
                .build()
            chain.proceed(request)
        }).addNetworkInterceptor(
            HttpLoggingInterceptor {
                Timber.tag(TIMBER_LOG_TAG).d(it)
            }.setLevel(HttpLoggingInterceptor.Level.BODY)
        ).build()
    }


    companion object {
        private const val AUTH_HEADER = "Authorization"
        private val AUTH_VALUE = "bearer " + TokenStorage.accessToken
        private const val USER_AGENT_HEADER = "User-Agent"
        private const val USER_AGENT_VALUE = "android:com.arektar.ktsRaddit:v1.0.0 (by u/arektar)"
        private const val TIMBER_LOG_TAG = "Network"


    }
}