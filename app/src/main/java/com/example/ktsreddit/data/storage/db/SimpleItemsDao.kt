package com.example.ktsreddit.data.storage.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SimpleItemsDao {

    @Insert
    fun insertItems(items:List<SimpleItemEnt>)

    @Query("SELECT * FROM SimpleItemEnt")
    fun getItems():List<SimpleItemEnt>

    @Query("SELECT * FROM SimpleItemEnt WHERE id=:id")
    fun getById(id:String):List<SimpleItemEnt>
}