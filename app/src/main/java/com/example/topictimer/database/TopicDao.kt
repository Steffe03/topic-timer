package com.example.topictimer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TopicDao {
    @Insert
    suspend fun insertAll(topics: List<Topic>)

    @Query("SELECT * FROM topics WHERE set_id = :setId")
    suspend fun getTopicsForSet(setId: Int): List<Topic>

    @Query("SELECT COUNT(*) FROM topics")
    suspend fun getCount(): Int
}