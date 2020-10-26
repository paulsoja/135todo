package com.paulsoia.todo135.business.repository

import com.paulsoia.todo135.business.model.task.Task

interface TaskTodoRepository {

    suspend fun saveTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun removeTask(taskId: Long)

    suspend fun getAllTasks(): Result<List<Task>>

}