package com.example.ktsreddit.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RedditDataResponse(
    @Json(name = "children")
    val children: List<RedditChildrenResponse>,
    @Json(name = "after")
    val after: String?,
    @Json(name = "before")
    val before: String?
)