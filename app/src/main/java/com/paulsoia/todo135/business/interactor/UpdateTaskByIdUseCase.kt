package com.paulsoia.todo135.business.interactor

import com.paulsoia.todo135.business.interactor.base.UseCase
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.repository.TaskRepository
import java.lang.Exception
import java.sql.SQLException

class UpdateTaskByIdUseCase(
    private val repository: TaskRepository
) : UseCase<UpdateTaskByIdUseCase.Params, Any>() {

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