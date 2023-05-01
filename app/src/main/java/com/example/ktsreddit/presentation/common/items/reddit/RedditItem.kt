package com.example.ktsreddit.presentation.common.items.reddit

abstract class RedditItem() {
    abstract val id: String

    fun id(): String {
        return when (this) {
            is RedditListSimpleItem -> this.t3_id
            is RedditListItemImage -> this.t3_id
            else -> {
                error("Bad RedditItem subclass")
            }
        }
    }

    fun getLikeStatus(): Boolean? {
        return when (this) {
            is RedditListSimpleItem -> this.likes
            is RedditListItemImage -> this.likes
            else -> {
                error("Bad RedditItem subclass")
            }
        }
    }

    fun setLikeStatus(likes: Boolean?):RedditItem {
        return when (this) {
            is RedditListSimpleItem ->
            this.copy(likes=likes)
            is RedditListItemImage ->
                this.copy(likes=likes)
            else -> {
                error("Bad RedditItem subclass")
            }
        }
    }

    fun setSavedStatus(saved: Boolean) {
        return when (this) {
            is RedditListSimpleItem -> this.saved = saved
            is RedditListItemImage -> this.saved = saved
            else -> {
                error("Bad RedditItem subclass")
            }
        }
    }

    fun plusScore(value: Int) {
        return when (this) {
            is RedditListSimpleItem -> this.score += value
            is RedditListItemImage -> this.score += value
            else -> {
                error("Bad RedditItem subclass")
            }
        }
    }

    fun updateScore(likes: Boolean) {
        if (likes) {
            when (this.getLikeStatus()) {
                true -> {
                    this.setLikeStatus(null)
                    this.plusScore(-1)
                }
                false -> {
                    this.setLikeStatus(true)
                    this.plusScore(2)
                }
                null -> {
                    this.setLikeStatus(true)
                    this.plusScore(1)
                }
            }
        } else {
            when (this.getLikeStatus()) {
                true -> {
                    this.setLikeStatus(false)
                    this.plusScore(-2)
                }
                false -> {
                    this.setLikeStatus(null)
                    this.plusScore(1)
                }
                null -> {
                    this.setLikeStatus(false)
                    this.plusScore(-1)
                }
            }
        }
    }

    fun getVoteDir(likes: Boolean): Int {
        return if (likes) {
            when (this.getLikeStatus()) {
                true -> 0
                false -> 1
                null -> 1
            }
        } else {
            when (this.getLikeStatus()) {
                true -> -1
                false -> 0
                null -> -1
            }
        }
    }

}