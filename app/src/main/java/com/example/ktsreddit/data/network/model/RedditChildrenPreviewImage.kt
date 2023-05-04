package com.swallow.cracker.data.model

import android.os.Parcelable
import com.example.ktsreddit.data.network.model.RedditChildrenPreviewImageSource
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
class RedditChildrenPreviewImage (
    @Json(name = "source")
    val source: RedditChildrenPreviewImageSource
) : Parcelable
