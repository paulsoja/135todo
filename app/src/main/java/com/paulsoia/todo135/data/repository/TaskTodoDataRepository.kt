package com.paulsoia.todo135.data.repository

import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.repository.TaskTodoRepository

class TaskTodoDataRepository(

) : TaskTodoRepository {

    override suspend fun saveSingleTask(task: Task) {
        TODO("Not yet implemented")
    }

}