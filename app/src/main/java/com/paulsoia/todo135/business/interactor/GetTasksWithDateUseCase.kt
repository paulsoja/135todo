package com.paulsoia.todo135.business.interactor

import com.paulsoia.todo135.business.interactor.base.UseCase
import com.paulsoia.todo135.business.model.task.LevelType
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.model.task.TaskMarker
import com.paulsoia.todo135.business.model.task.Title
import com.paulsoia.todo135.business.repository.TaskRepository
import com.paulsoia.todo135.presentation.utils.getDateTime
import java.lang.Exception
import java.sql.SQLException
import java.util.*

class GetTasksWithDateUseCase(
    private val repository: TaskRepository
) : UseCase<UseCase.None, Triple<List<TaskMarker>, List<TaskMarker>, List<TaskMarker>>>() {

    override suspend fun run(): Result<Triple<List<TaskMarker>, List<TaskMarker>, List<TaskMarker>>> {
        try {
            repository.getTasksWithDate().onSuccess {
                return Result.success(separateTasks(it))
            }.onFailure {
                return Result.failure(it)
            }
            return Result.failure(Throwable("GetTasksWithDateUseCase"))
        } catch (e: Exception) {
            return Result.failure(SQLException(e))
        }
    }

    private fun separateTasks(items: List<Task>): Triple<List<TaskMarker>, List<TaskMarker>, List<TaskMarker>> {
        val yesterday = mutableListOf<TaskMarker>()
        val today = mutableListOf<TaskMarker>()
        val tomorrow = mutableListOf<TaskMarker>()

        val calToday = Calendar.getInstance()
        val calYesterday = Calendar.getInstance()
        val calTomorrow = Calendar.getInstance()

        val todayDate = calToday.time.time.div(1000).getDateTime()

        calYesterday.add(Calendar.DATE, -1)
        val yesterdayDate = calYesterday.time.time.div(1000).getDateTime()

        calTomorrow.add(Calendar.DATE, -1)
        val tomorrowDate = calYesterday.time.time.div(1000).getDateTime()

        items.forEach {
            when(it.date.toLong().getDateTime()) {
                todayDate -> { if (today.size < 1) today.add(it) }
                yesterdayDate -> { if (yesterday.size < 3) yesterday.add(it) }
                tomorrowDate -> { if (tomorrow.size < 5) tomorrow.add(it) }
                else -> {}
            }
        }

        val yesterdayResult = mutableListOf<TaskMarker>()
        yesterdayResult.add(Title("Big"))
        yesterday.find { (it as Task).level == LevelType.BIG }.let {
            if (it is Task) yesterdayResult.add(it)
            else yesterdayResult.add(Task.empty())
        }
        yesterdayResult.add(Title("Medium"))
        yesterday.filter { (it as Task).level == LevelType.MEDIUM }.let {
            if (it.size < 3) {
                yesterdayResult.addAll(it)
                for (x in it.size until 3) yesterdayResult.add(Task.empty(level = LevelType.SMALL))
            }
            if (it.size >= 3) {
                yesterdayResult.addAll(it.take(3))
            }
        }
        yesterdayResult.add(Title("Small"))
        yesterday.filter { (it as Task).level == LevelType.SMALL }.let {
            if (it.size < 5) {
                yesterdayResult.addAll(it)
                for (x in it.size until 5) yesterdayResult.add(Task.empty(level = LevelType.SMALL))
            }
            if (it.size >= 5) {
                yesterdayResult.addAll(it.take(5))
            }
        }

        return Triple(yesterdayResult, today, tomorrow)
    }

}