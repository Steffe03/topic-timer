package com.example.topictimer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {
    @Insert
    suspend fun insertAll(topics: List<Topic>)

    @Insert
    suspend fun insertTopicSet(topicSet: TopicSet): Long

    @Query("SELECT * FROM topics WHERE set_id = :setId")
    suspend fun getTopicsForSet(setId: Int): List<Topic>

    @Query("SELECT COUNT(*) FROM topics")
    suspend fun getCount(): Int

    @Query("DELETE FROM topic_sets WHERE id = :setId")
    suspend fun removeTopicSet(setId: Int)

    @Query("SELECT id FROM topic_sets ORDER BY id ASC LIMIT 1")
    fun getInitialTopicSetId(): Flow<Int?>
}