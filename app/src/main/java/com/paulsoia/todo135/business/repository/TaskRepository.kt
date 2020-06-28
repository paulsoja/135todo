package com.paulsoia.todo135.business.repository

import com.paulsoia.todo135.business.model.task.Task

interface TaskRepository {

    suspend fun saveTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun removeTask(taskId: Long)

    suspend fun getTasksByDate(date: String): Result<List<Task>>

}