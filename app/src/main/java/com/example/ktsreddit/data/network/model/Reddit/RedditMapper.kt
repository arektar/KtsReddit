package com.example.ktsreddit.data.network.model.Reddit

import com.example.ktsreddit.presentation.common.items.reddit.LikeState.Companion.getLikedByBool
import com.example.ktsreddit.presentation.common.items.reddit.RedditItem
import com.example.ktsreddit.presentation.common.items.reddit.RedditListItemImage
import com.example.ktsreddit.presentation.common.items.reddit.RedditListSimpleItem

object RedditMapper {
    fun mapApiToUi(item: RedditNewsDataResponse): RedditItem {
        return when {
            item.preview?.enabled == true -> RedditListItemImage(
                id = item.id,
                t3_id = item.t3_id,
                author = item.author,
                subreddit = item.subreddit,
                title = item.title,
                selftext = item.selftext,
                score = item.score,
                likes = getLikedByBool(item.likes),
                saved = item.saved,
                numComments = item.numComments,
                created = item.created,
                thumbnail = item.thumbnail,
                url = item.url,
                preview = item.preview
            )
            else -> RedditListSimpleItem(
                id = item.id,
                t3_id = item.t3_id,
                author = item.author,
                subreddit = item.subreddit,
                title = item.title,
                selftext = item.selftext,
                score = item.score,
                likes = getLikedByBool(item.likes),
                saved = item.saved,
                numComments = item.numComments,
                created = item.created,
                url = item.url
            )
        }
    }
}