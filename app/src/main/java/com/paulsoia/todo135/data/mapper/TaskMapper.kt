package com.paulsoia.todo135.data.mapper

import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.data.database.entity.TaskEntity
import global.zakaz.stockman.data.mapper.base.Mapper

class TaskMapper : Mapper<TaskEntity, Task>() {

    override fun reverse(to: Task): TaskEntity {
        return TaskEntity(to.id, to.date, to.message, to.tag, to.category, to.level, to.isComplete)
    }

    override fun map(from: TaskEntity): Task {
        return Task(from.id, from.date, from.message, from.tag, from.category, from.level, from.isComplete)
    }

}