package com.example.ktsreddit.data.storage.db

import androidx.room.RoomDatabase
import androidx.room.Database
@Database(entities = arrayOf(SimpleItemEnt::class), version = 1)
abstract class Database:RoomDatabase() {
    abstract fun simpleItemsDao():SimpleItemsDao
}


