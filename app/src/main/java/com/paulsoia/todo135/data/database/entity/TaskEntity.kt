package com.paulsoia.todo135.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    var date: String,
    var message: String,
    var tag: String,
    var category: String,
    var level: String,
    var isComplete: Boolean
)