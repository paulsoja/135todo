package com.paulsoia.todo135.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tag_table")
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    var tag: String,
    var count: Int
)