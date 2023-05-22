package com.example.ktsreddit.data

import com.example.ktsreddit.presentation.common.items.reddit.RedditItem
import com.example.ktsreddit.data.network.Networking
import com.example.ktsreddit.data.network.model.Reddit.RedditMapper
import retrofit2.Response

class RedditRepository {

    suspend fun simpleGetSubreddit(
        subreddit: String,
        category: String,
        limit: Int
    ): List<RedditItem> {
        val response = Networking.redditApiOAuth.getSubreddit(
            subreddit = subreddit,
            category = category,
            limit = limit,
            count = REQUEST_COUNT,
            after = null,
            before = null
        )

        return checkNotNull(response.body()).data.children.map { RedditMapper.mapApiToUi(it.data) }
    }


    suspend fun savePost(category: String?, id: String) {
        Networking.redditApiOAuth.savedPost(category = category, id = id)
    }

    suspend fun unSavePost(id: String) {
        Networking.redditApiOAuth.unSavedPost(id = id)
    }

    suspend fun getUserInfo(): Response<Unit> {
        return Networking.redditApiOAuth.getMe()
    }

    suspend fun votePost(dir: Int, id: String) {
        Networking.redditApiOAuth.votePost(dir = dir.toString(), id = id)
    }

    companion object {
        const val REQUEST_COUNT: Int = 20
    }
}