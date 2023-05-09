package com.example.ktsreddit.presentation.common.items.reddit

abstract class RedditItem {
    abstract val id: String

    abstract fun id(): String
    abstract fun getLikeStatus(): LikeState
    abstract fun setLikeStatus(likes: LikeState): RedditItem
    abstract fun setSavedStatus(saved: Boolean): RedditItem
    abstract fun plusScore(value: Int): RedditItem
    abstract fun likeUpdateScore(newLikeState: LikeState): RedditItem

}
