package com.example.ktsreddit.data

import com.example.ktsreddit.presentation.common.items.reddit.RedditItem
import com.kts.github.data.network.Networking
import com.swallow.cracker.data.model.RedditMapper
import com.swallow.cracker.ui.model.QuerySubreddit
import kotlinx.coroutines.flow.*
import retrofit2.Response

class RedditRepository {





    suspend fun simpleGetSubreddit(
        subreddit: String,
        category: String,
        limit: String
    ): List<RedditItem> {
        val response = Networking.redditApiOAuth.getSubreddit(
            subreddit = subreddit,
            category = category,
            limit = limit,
            count = REQUEST_COUNT.toString(),
            after = null,
            before = null
        )

        //val responseBody = checkNotNull(response.body())
        //val after = responseBody.data.after
        //val before = params.key?.let { responseBody.data.before }

        return checkNotNull(response.body()).data.children.map { RedditMapper.mapApiToUi(it.data) }
    }




    suspend fun savePost(category: String?, id: String): Response<Unit> {
        return Networking.redditApiOAuth.savedPost(category = category, id = id)
    }

    suspend fun unSavePost(id: String): Response<Unit> {
        return Networking.redditApiOAuth.unSavedPost(id = id)
    }

    suspend fun votePost(dir: Int, id: String): Response<Unit> {
        return Networking.redditApiOAuth.votePost(dir = dir, id = id)
    }

    companion object {
        const val REQUEST_COUNT: Int = 20
    }
}