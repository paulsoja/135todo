package com.paulsoia.todo135.data.repository

import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.repository.TaskRepository
import com.paulsoia.todo135.data.database.dao.TaskDao
import com.paulsoia.todo135.data.mapper.TaskMapper

class TaskDataRepository(
    private val taskMapper: TaskMapper,
    private val taskDao: TaskDao
) : TaskRepository {

    override suspend fun saveTask(task: Task) {
        taskDao.saveTask(taskMapper.reverse(task))
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(taskMapper.reverse(task))
    }

    override suspend fun removeTask(taskId: Long) {
        taskDao.removeTask(taskId)
    }

    override suspend fun getAllTasks(): Result<List<Task>> {
        return try {
            Result.success(taskDao.getAllTasks().map {
                taskMapper.map(it)
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTasksByDate(date: String): Result<List<Task>> {
        return try {
            Result.success(taskDao.getTasksByDate(date).map {
                taskMapper.map(it)
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTasksWithDate(): Result<List<Task>> {
        return try {
            Result.success(taskDao.getTasksWithDate().map {
                taskMapper.map(it)
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}