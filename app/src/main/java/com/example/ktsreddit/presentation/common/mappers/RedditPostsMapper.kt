package com.example.ktsreddit.presentation.common.mappers

import com.example.ktsreddit.data.storage.db.PostEnt
import com.example.ktsreddit.presentation.common.items.reddit.LikeState
import com.example.ktsreddit.presentation.common.items.reddit.RedditPost

object RedditPostsMapper : Mapper<RedditPost, PostEnt> {

    override fun map(item: RedditPost): PostEnt {
        return PostEnt(
            id = item.id,
            t3_id = item.t3_id,
            author = item.author,
            subreddit = item.subreddit,
            title = item.title,
            selftext = item.selftext,
            score = item.score,
            likes = LikeState.getBoolFromLike(item.likes),
            saved = item.saved,
            numComments = item.numComments,
            created = item.created,
            url = item.url
        )
    }
}