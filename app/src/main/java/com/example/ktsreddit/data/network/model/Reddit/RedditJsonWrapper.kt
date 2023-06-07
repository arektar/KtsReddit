package com.example.ktsreddit.data.network.model.Reddit

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RedditJsonWrapper<T>(
    val data: T
)