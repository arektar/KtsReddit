package com.example.ktsreddit.data.network

import com.kts.github.data.auth.TokenStorage

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import timber.log.Timber

object Networking {

    private var okhttpClientOauth: OkHttpClient =
        OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder()

            if (TokenStorage.accessToken != null) {
                builder
                    .addHeader("Authorization", "bearer " + TokenStorage.accessToken)
                    .addHeader("User-Agent", "android:com.arektar.ktsRaddit:v1.0.0 (by u/arektar)")
            }

            val request = builder.method(original.method, original.body)
                .build()
            chain.proceed(request)
        }).addNetworkInterceptor(
            HttpLoggingInterceptor {
                Timber.tag("Network").d(it)
            }.setLevel(HttpLoggingInterceptor.Level.BODY)
        ).build()

    private val retrofitOAuth = Retrofit.Builder()
        .client(okhttpClientOauth)
        .baseUrl("https://oauth.reddit.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val redditApiOAuth: RedditApi = retrofitOAuth.create()

}

