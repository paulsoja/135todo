package com.paulsoia.todo135.business.interactor

import com.paulsoia.todo135.business.interactor.base.UseCase
import com.paulsoia.todo135.business.repository.TaskTodoRepository
import java.sql.SQLException

class DeleteTodoTaskByIdUseCase(
    private val repository: TaskTodoRepository
) : UseCase<DeleteTodoTaskByIdUseCase.Params, Any>() {

    data class Params(val taskId: Long)

    override suspend fun run(): Result<Any> {
        if (params == null) throw IllegalArgumentException("Parameter required")
        return try {
            params!!.run {
                repository.removeTask(taskId)
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(SQLException(e))
        }
    }

}