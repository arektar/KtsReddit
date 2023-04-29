package com.example.ktsreddit.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ktsreddit.presentation.common.items.reddit.RedditItem
import com.kts.github.data.network.Networking
import com.swallow.cracker.data.model.RedditMapper

class RedditPagingSource(
    private val subreddit: String,
    private val category: String,
    private val limit: String
) : PagingSource<String, RedditItem>() {

    override fun getRefreshKey(state: PagingState<String, RedditItem>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, RedditItem> {
        val pageSize: Int = params.loadSize

        return try {
            val response = Networking.redditApiOAuth.getSubreddit(
                subreddit = subreddit,
                category = category,
                limit = limit,
                count = pageSize.toString(),
                after = params.key,
                before = null
            )

            val responseBody = checkNotNull(response.body())
            val after = responseBody.data.after
            val before = params.key?.let { responseBody.data.before }

            val data =
                checkNotNull(response.body()).data.children.map { RedditMapper.mapApiToUi(it.data) }

            LoadResult.Page(
                data,
                before,
                after
            )
        } catch (exception: Throwable) {
            LoadResult.Error(exception)
        }
    }

    override val keyReuseSupported: Boolean = true
}
