package com.paulsoia.todo135.business.interactor

import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.repository.TaskRepository
import global.zakaz.stockman.domain.interactor.base.UseCase
import java.lang.Exception
import java.sql.SQLException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class GetStatsByDatesUseCase(
    private val repository: TaskRepository
) : UseCase<GetStatsByDatesUseCase.Params, List<Task>>() {

    data class Params(val firstDate: Date, val secondDate: Date)

    override suspend fun run(): Result<List<Task>> {
        if (params == null) throw IllegalArgumentException("Parameter required")
        return try {
            params!!.run {
                /*repository.getAllTasks().onSuccess {
                    val available = mutableListOf<Task>()
                    it.filter {
                        if (firstDate < convertISOTimeToDate(it.date)) {

                            }
                    }
                }*/
                Result.success(listOf())
                //отфильтровать по выбранным датам
            }
        } catch (e: Exception) {
            Result.failure(SQLException(e))
        }
    }

}