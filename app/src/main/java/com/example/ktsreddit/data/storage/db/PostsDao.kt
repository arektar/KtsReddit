package com.example.ktsreddit.data.storage.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostsDao {

    @Insert
    fun insertItems(items:List<PostEnt>)

    @Query("SELECT * FROM PostEnt")
    fun getItems():List<PostEnt>

    @Query("SELECT * FROM PostEnt WHERE id=:id")
    fun getById(id:String):List<PostEnt>
}