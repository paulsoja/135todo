package com.paulsoia.todo135.business.interactor

import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.repository.TaskRepository
import global.zakaz.stockman.domain.interactor.base.UseCase
import java.lang.Exception
import java.sql.SQLException

class GetTaskByDateUseCase(
    private val repository: TaskRepository
) : UseCase<GetTaskByDateUseCase.Params, List<Task>>() {

    data class Params(val date: String)

    override suspend fun run(): Result<List<Task>> {
        if (params == null) throw IllegalArgumentException("Parameter required")
        return try {
            params!!.run {
                repository.getTasksByDate(date)
            }
        } catch (e: Exception) {
            Result.failure(SQLException(e))
        }
    }

}