package com.example.topictimer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.topictimer.tappleTopics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Topic::class, TopicSet::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun topicDao(): TopicDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "topic_database"
                )
                    .addCallback(TopicDatabaseCallback(scope))
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

    private class TopicDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.topicDao())
                }
            }
        }

        suspend fun populateDatabase(dao: TopicDao) {
            val setId1 = dao.insertTopicSet(TopicSet(name = "First set")).toInt()
            val setId2 = dao.insertTopicSet(TopicSet(name = "Second set")).toInt()
            val setId3 = dao.insertTopicSet(TopicSet(name = "Third set")).toInt()

            val entities1 = tappleTopics.take(50).shuffled().map{description -> Topic(description = description, setId = setId1) }
            val entities2 = tappleTopics.slice(51..100).shuffled().map { description -> Topic(description = description, setId = setId2) }
            val entities3 = tappleTopics.slice(101..150).shuffled().map { description -> Topic(description = description, setId = setId3) }

            dao.insertAll(entities1)
            dao.insertAll(entities2)
            dao.insertAll(entities3)
        }
    }
}