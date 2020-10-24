package com.paulsoia.todo135.business.interactor

import com.paulsoia.todo135.business.interactor.base.UseCase
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.repository.TaskRepository
import java.lang.Exception
import java.sql.SQLException
import java.util.*

class GetStatsByDatesUseCase(
    private val repository: TaskRepository
) : UseCase<GetStatsByDatesUseCase.Params, List<Task>>() {

    data class Params(val firstDate: Date?, val secondDate: Date?)

    override suspend fun run(): Result<List<Task>> {
        if (params == null) throw IllegalArgumentException("Parameter required")
        return try {
            params!!.run {
                val available = mutableListOf<Task>()
                repository.getAllTasks().onSuccess {
                    val ttt = it.size
                    val uuu = it
                    val kkk = ttt
                    val fff = firstDate?.time?.div(1000)
                    val sss = secondDate?.time?.div(1000)
                    it.forEach {
                        if (fff != null && sss != null) {
                            if (it.date in fff..sss) {
                                available.add(it)
                            }
                        } else if (sss == null) {
                            if (it.date.toLong() == fff) {
                                available.add(it)
                            }
                        }
                    }
                }
                Result.success(available)
            }
        } catch (e: Exception) {
            Result.failure(SQLException(e))
        }
    }

}