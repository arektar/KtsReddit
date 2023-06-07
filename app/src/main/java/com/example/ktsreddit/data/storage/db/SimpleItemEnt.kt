package com.example.ktsreddit.data.storage.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ktsreddit.presentation.common.items.reddit.LikeState
import com.example.ktsreddit.presentation.common.items.reddit.RedditListSimpleItem

@Entity
data class SimpleItemEnt (
    @PrimaryKey val id: String,
    @ColumnInfo(name = "t3_id") val t3_id: String,
    @ColumnInfo(name = "author") val author: String,
    @ColumnInfo(name = "subreddit") val subreddit: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "selftext") val selftext: String,
    @ColumnInfo(name = "score") val score: Int,
    @ColumnInfo(name = "likes") val likes: Boolean?,
    @ColumnInfo(name = "saved") val saved: Boolean,
    @ColumnInfo(name = "numComments") val numComments: Int,
    @ColumnInfo(name = "created") val created: Long,
    @ColumnInfo(name = "url") val url: String
){
    fun getConvertToItem(): RedditListSimpleItem {
        return RedditListSimpleItem(
            id = this.id,
            t3_id = this.t3_id,
            author = this.author,
            subreddit = this.subreddit,
            title = this.title,
            selftext = this.selftext,
            score = this.score,
            likes = LikeState.getLikedByBool(this.likes),
            saved = this.saved,
            numComments = this.numComments,
            created = this.created,
            url = this.url
        )
    }


    companion object {


        fun getConvertFromItem(item: RedditListSimpleItem): SimpleItemEnt {
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
}