package com.paulsoia.todo135.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paulsoia.todo135.data.database.dao.TaskDao
import com.paulsoia.todo135.data.database.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getTasksDao(): TaskDao

}