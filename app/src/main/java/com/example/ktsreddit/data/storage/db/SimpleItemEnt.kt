package com.example.ktsreddit.data.storage.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SimpleItemEnt(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "t3_id")
    val t3_id: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "subreddit")
    val subreddit: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "selftext")
    val selftext: String,
    @ColumnInfo(name = "score")
    val score: Int,
    @ColumnInfo(name = "likes")
    val likes: Boolean?,
    @ColumnInfo(name = "saved")
    val saved: Boolean,
    @ColumnInfo(name = "numComments")
    val numComments: Int,
    @ColumnInfo(name = "created")
    val created: Long,
    @ColumnInfo(name = "url")
    val url: String
)