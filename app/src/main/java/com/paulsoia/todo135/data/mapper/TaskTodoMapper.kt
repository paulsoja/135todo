package com.paulsoia.todo135.data.mapper

import com.paulsoia.todo135.business.model.task.LevelType
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.data.database.entity.TaskTodoEntity
import com.paulsoia.todo135.presentation.utils.getEnumTypeValue
import global.zakaz.stockman.data.mapper.base.Mapper

class TaskTodoMapper : Mapper<TaskTodoEntity, Task>() {

    override fun reverse(to: Task): TaskTodoEntity {
        return with(to) {
            TaskTodoEntity(id, date, message, tag, category, level.type, isComplete)
        }
    }

    override fun map(from: TaskTodoEntity): Task {
        return with(from) {
            val levelType = getEnumTypeValue(level) ?: LevelType.NONE
            Task(id, date, message, tag, category, levelType, isComplete)
        }
    }
}