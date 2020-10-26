package com.paulsoia.todo135.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_todo")
data class TaskTodoEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    var date: Int,
    var message: String,
    var tag: String,
    var category: String,
    var level: String,
    var isComplete: Boolean
)