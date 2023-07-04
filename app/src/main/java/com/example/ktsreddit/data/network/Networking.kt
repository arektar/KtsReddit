package com.example.ktsreddit.data.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object Networking {

    private var okhttpClientOauth: OkHttpClient = OkHttpClientOauth().getClient()


    private val retrofitOAuth = Retrofit.Builder()
        .client(okhttpClientOauth)
        .baseUrl("https://oauth.reddit.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val redditApiOAuth: RedditApi = retrofitOAuth.create()

    fun updateClient(){
        okhttpClientOauth = OkHttpClientOauth().getClient()
    }

}

