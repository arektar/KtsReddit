package com.example.ktsreddit.data

import com.example.ktsreddit.data.network.Networking
import com.example.ktsreddit.data.network.model.Reddit.RedditMapper
import com.example.ktsreddit.data.storage.db.DatabaseWorker
import com.example.ktsreddit.data.storage.shared.KeyValueStorage
import com.example.ktsreddit.presentation.common.items.reddit.QuerySubreddit
import com.example.ktsreddit.presentation.common.items.reddit.RedditItem
import com.example.ktsreddit.presentation.common.items.reddit.RedditPost
import org.koin.core.component.KoinComponent
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


    var dbWorker = DatabaseWorker

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

    suspend fun getPosts(
        query: QuerySubreddit,
        setFromDbStatus: (Boolean) -> Unit
    ): List<RedditItem> {
        try {
            val items = simpleGetSubreddit(query.subreddit, query.category, query.limit)
            dbWorker.savePosts(items.filterIsInstance<RedditPost>())
            setFromDbStatus(false)

            return items

        } catch (e: Exception) {
            val items = dbWorker.getPosts()
            setFromDbStatus(true)
            KeyValueStorage.setAuthToken(null)
            KeyValueStorage.setRefreshToken(null)
            return items
        }
    }


    companion object {
        const val REQUEST_COUNT: Int = 20
    }
}