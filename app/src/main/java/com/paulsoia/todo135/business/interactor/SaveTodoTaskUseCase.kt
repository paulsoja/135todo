package com.paulsoia.todo135.business.interactor

import com.paulsoia.todo135.business.interactor.base.UseCase
import com.paulsoia.todo135.business.model.task.Task
import java.lang.Exception
import java.sql.SQLException

class SaveTodoTaskUseCase : UseCase<SaveTodoTaskUseCase.Params, Any>() {

    data class Params(val task: Task)

    override suspend fun run(): Result<Any> {
        if (params == null) throw IllegalArgumentException("Parameter required")

        return try {
            params!!.run {
                //repository.saveTask(task)
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(SQLException(e))
        }
    }

}