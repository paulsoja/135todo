package com.paulsoia.todo135.business.interactor

import com.paulsoia.todo135.business.interactor.base.UseCase
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.repository.TaskTodoRepository
import java.lang.Exception
import java.sql.SQLException

class UpdateTodoTaskByIdUseCase(
    private val repository: TaskTodoRepository
) : UseCase<UpdateTodoTaskByIdUseCase.Params, Any>() {

    data class Params(val task: Task)

    override suspend fun run(): Result<Any> {
        if (params == null) throw IllegalArgumentException("Parameter required")
        return try {
            params!!.run {
                repository.updateTask(task)
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(SQLException(e))
        }
    }
}