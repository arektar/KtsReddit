package com.example.ktsreddit.data.storage.db

import androidx.room.Room
import com.example.ktsreddit.app.KtsRedditApplication
import com.example.ktsreddit.presentation.common.items.reddit.RedditListSimpleItem

object DatabaseWorker {
    private val db = Room.databaseBuilder(KtsRedditApplication.appContext,Database::class.java, "db").build()
    private val simpleItemsDao = db.simpleItemsDao()

    fun saveSimpleItems(items: List<RedditListSimpleItem>){
        val itemsEnts = items.map { SimpleItemEnt.getConvertFromItem(it) }.filter { simpleItemsDao.getById(it.id).isEmpty() }

        simpleItemsDao.insertItems(itemsEnts)
    }

    fun getSimpleItems(): List<RedditListSimpleItem> {
        val items: List<SimpleItemEnt> = simpleItemsDao.getItems()
        return items.map { it.getConvertToItem() }
    }
}