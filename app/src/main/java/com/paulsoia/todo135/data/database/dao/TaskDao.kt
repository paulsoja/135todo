package com.paulsoia.todo135.data.database.dao

import androidx.room.*
import com.paulsoia.todo135.data.database.entity.TaskEntity

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTask(task: TaskEntity): Long

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Query("DELETE FROM task WHERE id LIKE :taskId")
    suspend fun removeTask(taskId: Long)

    @Query("SELECT * FROM task")
    suspend fun getAllTasks(): List<TaskEntity>

    @Query("SELECT * FROM task WHERE id = :taskId")
    suspend fun getTaskById(taskId: Long): TaskEntity

    @Transaction
    @Query("SELECT * FROM task WHERE date = :date")
    suspend fun getTasksByDate(date: Int): List<TaskEntity>

    @Transaction
    @Query("SELECT * FROM task WHERE date IS NOT NULL AND date != 0")
    suspend fun getTasksWithDate(): List<TaskEntity>

}