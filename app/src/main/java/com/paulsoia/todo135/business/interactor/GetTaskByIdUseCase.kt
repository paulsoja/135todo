package com.paulsoia.todo135.business.interactor

import com.paulsoia.todo135.business.interactor.base.UseCase
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.repository.TaskRepository
import java.lang.Exception
import java.sql.SQLException

class GetTaskByIdUseCase(
    private val repository: TaskRepository
) : UseCase<GetTaskByIdUseCase.Params, Task>() {

    data class Params(val taskId: Long?)

    override suspend fun run(): Result<Task> {
        if (params == null) throw IllegalArgumentException("Parameter required")
        return try {
            params!!.run {
                repository.getTaskById(this.taskId ?: -1)
            }
        } catch (e: Exception) {
            Result.failure(SQLException(e))
        }
    }

}