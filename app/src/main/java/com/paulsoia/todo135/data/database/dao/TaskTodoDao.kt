package com.paulsoia.todo135.data.database.dao

import androidx.room.*
import com.paulsoia.todo135.data.database.entity.TaskTodoEntity

@Dao
interface TaskTodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTask(task: TaskTodoEntity): Long

    @Update
    suspend fun updateTask(task: TaskTodoEntity)

    @Query("DELETE FROM task_todo WHERE id LIKE :taskId")
    suspend fun removeTask(taskId: Long)

    @Query("SELECT * FROM task_todo")
    suspend fun getAllTasks(): List<TaskTodoEntity>

}