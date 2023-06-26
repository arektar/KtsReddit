package com.example.ktsreddit.data.common.mappers

import com.example.ktsreddit.data.storage.db.SimpleItemEnt
import com.example.ktsreddit.presentation.common.items.reddit.LikeState
import com.example.ktsreddit.presentation.common.items.reddit.RedditListSimpleItem

object RedditListSimpleItemMapper : Mapper<RedditListSimpleItem, SimpleItemEnt> {

    override fun map(item: RedditListSimpleItem): SimpleItemEnt {
        return SimpleItemEnt(
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