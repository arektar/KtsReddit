package com.example.ktsreddit.presentation.common.items.reddit

import android.os.Parcelable
import com.example.ktsreddit.data.network.model.Reddit.RedditChildrenPreview
import com.example.ktsreddit.presentation.common.utils.convertLongToTime
import kotlinx.parcelize.Parcelize

@Parcelize
data class RedditListItemImage(
    override val id: String,
    val t3_id: String,
    val author: String,
    val subreddit: String,
    val title: String,
    val selftext: String,
    val score: Int,
    val likes: LikeState,
    val saved: Boolean,
    val numComments: Int,
    val created: Long,
    val thumbnail: String,
    val url: String,
    val preview: RedditChildrenPreview?
) : RedditItem(), Parcelable {

    val time: String
        get() = created.convertLongToTime()

    override fun id(): String {
            return this.t3_id
        }


    override fun getLikeStatus(): LikeState {
        return this.likes
    }

    override fun setLikeStatus(likes: LikeState): RedditItem {
        return this.copy(likes = likes)
    }

    override fun setSavedStatus(saved: Boolean): RedditItem {
        return this.copy(saved = saved)
    }

    override fun plusScore(value: Int): RedditItem {
        return this.copy(score = this.score + value)
    }

    override fun likeUpdateScore(newLikeState: LikeState): RedditItem {
        val oldLikeState = this.getLikeStatus()
        var newItem = this.plusScore(-oldLikeState.plusScore)
        newItem = newItem.plusScore(+newLikeState.plusScore)
        newItem = newItem.setLikeStatus(newLikeState)
        return newItem
    }
}