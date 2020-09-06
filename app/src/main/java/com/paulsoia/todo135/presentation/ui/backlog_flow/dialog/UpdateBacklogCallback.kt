package com.paulsoia.todo135.presentation.ui.backlog_flow.dialog

import com.paulsoia.todo135.business.model.task.Task

interface UpdateBacklogCallback {
    fun onUpdateTask(task: Task? = null)
}