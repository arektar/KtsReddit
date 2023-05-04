package com.example.ktsreddit.presentation.common.items.reddit

enum class LikeState(var isLike: Boolean?, var plusScore: Int, var likeVote: Int) {
    Liked(true, 1, 1) {
        override fun liked(): LikeState = NotSet
        override fun disliked(): LikeState = NotSet
    },
    Disliked(false, -1, -1) {
        override fun liked(): LikeState = NotSet
        override fun disliked(): LikeState = NotSet
    },
    NotSet(null, 0, 0) {
        override fun liked(): LikeState = Liked
        override fun disliked(): LikeState = Disliked
    };

    abstract fun liked(): LikeState
    abstract fun disliked(): LikeState

    companion object {
        fun getLikedByBool(liked: Boolean?): LikeState {
            return when (liked) {
                false -> Disliked
                null -> NotSet
                true -> Liked
            }
        }

    }


}