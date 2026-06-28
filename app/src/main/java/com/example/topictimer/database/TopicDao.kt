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
    fun getTopicsForSet(setId: Int): Flow<List<Topic>>

    @Query("SELECT COUNT(*) FROM topics")
    suspend fun getCount(): Int

    @Query("DELETE FROM topic_sets WHERE id = :setId")
    suspend fun removeTopicSet(setId: Int)

    @Query("SELECT id FROM topic_sets ORDER BY id ASC LIMIT 1")
    fun getInitialTopicSetId(): Flow<Int?>

    @Query("""
        SELECT topic_sets.*,(SELECT COUNT(*) FROM topics WHERE topics.set_id = topic_sets.id) AS topicCount 
        FROM topic_sets
    """)
    fun getAllTopicSets(): Flow<List<TopicSetWithCount>>

    @Query("DELETE FROM topics WHERE id = :id")
    suspend fun removeTopic(id: Int)
}