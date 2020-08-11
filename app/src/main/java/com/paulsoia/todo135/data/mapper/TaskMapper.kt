package com.paulsoia.todo135.data.mapper

import com.paulsoia.todo135.business.model.task.LevelType
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.data.database.entity.TaskEntity
import com.paulsoia.todo135.presentation.utils.getEnumTypeValue
import global.zakaz.stockman.data.mapper.base.Mapper

class TaskMapper : Mapper<TaskEntity, Task>() {

    override fun reverse(to: Task): TaskEntity {
        return with(to) {
            TaskEntity(id, date, message, tag, category, level.type, isComplete)
        }
    }

    override fun map(from: TaskEntity): Task {
        return with(from) {
            val levelType = getEnumTypeValue<LevelType>(level) ?: LevelType.NONE
            Task(id, date, message, tag, category, levelType, isComplete)
        }
    }

}