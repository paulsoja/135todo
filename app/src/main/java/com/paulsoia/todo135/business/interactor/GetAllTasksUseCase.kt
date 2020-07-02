package com.paulsoia.todo135.business.interactor

import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.repository.TaskRepository
import global.zakaz.stockman.domain.interactor.base.UseCase
import java.lang.Exception
import java.sql.SQLException

class GetAllTasksUseCase(
    private val repository: TaskRepository
) : UseCase<UseCase.None, List<Task>>() {

    override suspend fun run(): Result<List<Task>> {
        return try {
            repository.getAllTasks()
        } catch (e: Exception) {
            Result.failure(SQLException(e))
        }
    }

}