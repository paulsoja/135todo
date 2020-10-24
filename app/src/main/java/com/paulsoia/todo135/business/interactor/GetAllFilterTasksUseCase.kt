package com.paulsoia.todo135.business.interactor

import com.paulsoia.todo135.business.interactor.base.UseCase
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.model.task.TaskMarker
import com.paulsoia.todo135.business.repository.TaskRepository
import java.lang.Exception
import java.sql.SQLException

class GetAllFilterTasksUseCase(
    private val repository: TaskRepository
) : UseCase<UseCase.None, List<TaskMarker>>() {

    override suspend fun run(): Result<List<TaskMarker>> {
        return try {
            val items = repository.getAllTasks()
            Result.success(mergeList(items))
        } catch (e: Exception) {
            Result.failure(SQLException(e))
        }
    }

    private fun mergeList(items: Result<List<Task>>): List<TaskMarker> {
        val result = mutableListOf<TaskMarker>()

        items.map {
            //result.add(Title("to do"))
            result.addAll(it.filter { !it.isComplete })
            //result.add(Title("complete"))
            result.addAll(it.filter { it.isComplete })
        }

        return result
    }
}