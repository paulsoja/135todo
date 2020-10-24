package com.paulsoia.todo135.business.repository

import com.paulsoia.todo135.business.model.task.Task

interface TaskTodoRepository {

    suspend fun saveSingleTask(task: Task)

}