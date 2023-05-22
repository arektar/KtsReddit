package com.example.ktsreddit.data.network.model.Reddit

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RedditChildrenResponse(
    @Json(name = "data")
    val data: RedditNewsDataResponse
)