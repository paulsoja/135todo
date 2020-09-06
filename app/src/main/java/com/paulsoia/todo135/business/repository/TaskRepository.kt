package com.paulsoia.todo135.business.repository

import com.paulsoia.todo135.business.model.tag.Tag
import com.paulsoia.todo135.business.model.task.Task

interface TaskRepository {

    suspend fun saveTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun removeTask(taskId: Long)

    suspend fun getAllTasks(): Result<List<Task>>

    suspend fun getTaskById(taskId: Long): Result<Task>

    suspend fun getTasksByDate(date: String): Result<List<Task>>

    suspend fun getTasksWithDate(): Result<List<Task>>

    suspend fun saveTag(tag: Tag)

    suspend fun updateTag(tag: Tag)

    suspend fun removeTagById(tagId: Long)

    suspend fun getAllTags(): Result<List<Tag>>

}