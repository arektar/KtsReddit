package com.example.ktsreddit.presentation.common.items.reddit

import android.os.Parcelable
import com.example.ktsreddit.data.network.model.RedditChildrenPreview
import com.swallow.cracker.utils.convertLongToTime
import kotlinx.parcelize.Parcelize

@Parcelize
data class RedditListItemImage(
    override var id: String,
    var t3_id: String,
    var author: String,
    var subreddit: String,
    var title: String,
    var selftext: String,
    var score: Int,
    var likes: Boolean?,
    var saved: Boolean,
    var numComments: Int,
    var created: Long,
    var thumbnail: String,
    var url: String,
    val preview: RedditChildrenPreview?
) : RedditItem(), Parcelable {

    val time: String
        get() = created.convertLongToTime()
}