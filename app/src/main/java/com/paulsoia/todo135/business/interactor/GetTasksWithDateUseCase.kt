package com.paulsoia.todo135.business.interactor

import com.paulsoia.todo135.business.interactor.base.UseCase
import com.paulsoia.todo135.business.model.task.LevelType
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.model.task.TaskMarker
import com.paulsoia.todo135.business.model.task.Title
import com.paulsoia.todo135.business.repository.TaskTodoRepository
import com.paulsoia.todo135.presentation.utils.getDateTime
import java.lang.Exception
import java.sql.SQLException
import java.util.*

class GetTasksWithDateUseCase(
    private val repository: TaskTodoRepository
) : UseCase<UseCase.None, Triple<List<TaskMarker>, List<TaskMarker>, List<TaskMarker>>>() {

    override suspend fun run(): Result<Triple<List<TaskMarker>, List<TaskMarker>, List<TaskMarker>>> {
        try {
            repository.getAllTasks().onSuccess {
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

        calTomorrow.add(Calendar.DATE, +1)
        val tomorrowDate = calTomorrow.time.time.div(1000).getDateTime()

        items.forEach {
            when(it.date.toLong().getDateTime()) {
                todayDate -> { today.add(it) }
                yesterdayDate -> { if (yesterday.size < 9) yesterday.add(it) }
                tomorrowDate -> { if (tomorrow.size < 9) tomorrow.add(it) }
                else -> {}
            }
        }

        return Triple(yesterdayCollect(yesterday), todayCollect(today), tomorrowCollect(tomorrow))
    }

    private fun yesterdayCollect(yesterday: MutableList<TaskMarker>): MutableList<TaskMarker> {
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
                for (x in it.size until 3) yesterdayResult.add(Task.empty(level = LevelType.MEDIUM))
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
        return yesterdayResult
    }

    private fun todayCollect(today: MutableList<TaskMarker>): MutableList<TaskMarker> {
        val todayResult = mutableListOf<TaskMarker>()
        todayResult.add(Title("Big"))
        today.find { (it as Task).level == LevelType.BIG }.let {
            if (it is Task) todayResult.add(it)
            else todayResult.add(Task.empty())
        }
        todayResult.add(Title("Medium"))
        today.filter { (it as Task).level == LevelType.MEDIUM }.let {
            if (it.size < 3) {
                todayResult.addAll(it)
                for (x in it.size until 3) todayResult.add(Task.empty(level = LevelType.MEDIUM))
            }
            if (it.size >= 3) {
                todayResult.addAll(it.take(3))
            }
        }
        todayResult.add(Title("Small"))
        today.filter { (it as Task).level == LevelType.SMALL }.let {
            if (it.size < 5) {
                todayResult.addAll(it)
                for (x in it.size until 5) todayResult.add(Task.empty(level = LevelType.SMALL))
            }
            if (it.size >= 5) {
                todayResult.addAll(it.take(5))
            }
        }
        return todayResult
    }

    private fun tomorrowCollect(tomorrow: MutableList<TaskMarker>): MutableList<TaskMarker> {
        val tomorrowResult = mutableListOf<TaskMarker>()
        tomorrowResult.add(Title("Big"))
        tomorrow.find { (it as Task).level == LevelType.BIG }.let {
            if (it is Task) tomorrowResult.add(it)
            else tomorrowResult.add(Task.empty())
        }
        tomorrowResult.add(Title("Medium"))
        tomorrow.filter { (it as Task).level == LevelType.MEDIUM }.let {
            if (it.size < 3) {
                tomorrowResult.addAll(it)
                for (x in it.size until 3) tomorrowResult.add(Task.empty(level = LevelType.MEDIUM))
            }
            if (it.size >= 3) {
                tomorrowResult.addAll(it.take(3))
            }
        }
        tomorrowResult.add(Title("Small"))
        tomorrow.filter { (it as Task).level == LevelType.SMALL }.let {
            if (it.size < 5) {
                tomorrowResult.addAll(it)
                for (x in it.size until 5) tomorrowResult.add(Task.empty(level = LevelType.SMALL))
            }
            if (it.size >= 5) {
                tomorrowResult.addAll(it.take(5))
            }
        }
        return tomorrowResult
    }

}