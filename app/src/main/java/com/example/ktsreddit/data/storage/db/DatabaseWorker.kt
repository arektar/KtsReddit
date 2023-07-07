package com.example.ktsreddit.data.storage.db

import androidx.room.Room
import com.example.ktsreddit.app.KtsRedditApplication
import com.example.ktsreddit.presentation.common.mappers.RedditPostsMapper
import com.example.ktsreddit.presentation.common.mappers.PostMapper
import com.example.ktsreddit.presentation.common.items.reddit.RedditPost

object DatabaseWorker {
    private val db =
        Room.databaseBuilder(KtsRedditApplication.appContext, Database::class.java, "db").allowMainThreadQueries().fallbackToDestructiveMigration().build()
    private val postsDao = db.PostsDao()

    fun savePosts(items: List<RedditPost>) {
        val postsEnts = items.map { RedditPostsMapper.map(it) }
            .filter { postsDao.getById(it.id).isEmpty() }

        postsDao.insertItems(postsEnts)
    }

    fun getPosts(): List<RedditPost> {
        val items: List<PostEnt> = postsDao.getItems()
        return items.map { PostMapper.map(it) }
    }
}