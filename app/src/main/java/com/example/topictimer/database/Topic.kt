package com.example.topictimer.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "topics",
    foreignKeys = [ForeignKey(
        entity = TopicSet::class,
        parentColumns = ["id"],
        childColumns = ["set_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Topic(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "set_id", index = true) val setId: Int
)