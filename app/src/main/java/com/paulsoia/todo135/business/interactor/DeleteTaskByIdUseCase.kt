package com.paulsoia.todo135.business.interactor

import com.paulsoia.todo135.business.repository.TaskRepository
import global.zakaz.stockman.domain.interactor.base.UseCase
import java.sql.SQLException

class DeleteTaskByIdUseCase(
    private val repository: TaskRepository
) : UseCase<DeleteTaskByIdUseCase.Params, Any>() {

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