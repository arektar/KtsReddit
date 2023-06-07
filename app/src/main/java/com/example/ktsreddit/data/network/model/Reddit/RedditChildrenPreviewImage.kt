package com.example.ktsreddit.data.network.model.Reddit

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
class RedditChildrenPreviewImage (
    @Json(name = "source")
    val source: RedditChildrenPreviewImageSource
) : Parcelable
