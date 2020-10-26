package com.paulsoia.todo135.data.repository

import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.repository.TaskTodoRepository
import com.paulsoia.todo135.data.database.dao.TaskTodoDao
import com.paulsoia.todo135.data.mapper.TaskTodoMapper

class TaskTodoDataRepository(
    private val taskMapper: TaskTodoMapper,
    private val taskTodoDao: TaskTodoDao,
) : TaskTodoRepository {

    override suspend fun saveTask(task: Task) {
        taskTodoDao.saveTask(taskMapper.reverse(task))
    }

    override suspend fun updateTask(task: Task) {
        taskTodoDao.updateTask(taskMapper.reverse(task))
    }

    override suspend fun removeTask(taskId: Long) {
        taskTodoDao.removeTask(taskId)
    }

    override suspend fun getAllTasks(): Result<List<Task>> {
        return try {
            Result.success(taskTodoDao.getAllTasks().map {
                taskMapper.map(it)
            })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}