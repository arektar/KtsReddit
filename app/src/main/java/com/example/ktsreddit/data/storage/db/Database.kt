package com.example.ktsreddit.data.storage.db

import androidx.room.RoomDatabase
import androidx.room.Database
@Database(entities = arrayOf(PostEnt::class), version = 2)
abstract class Database:RoomDatabase() {
    abstract fun PostsDao():PostsDao
}


