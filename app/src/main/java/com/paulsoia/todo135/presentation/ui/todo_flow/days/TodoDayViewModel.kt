package com.paulsoia.todo135.presentation.ui.todo_flow.days

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulsoia.todo135.business.interactor.UpdateTaskByIdUseCase
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.model.task.TaskMarker

class TodoDayViewModel(
    private val updateTaskByIdUseCase: UpdateTaskByIdUseCase
) : ViewModel() {

    internal val items = MutableLiveData<List<TaskMarker>>()
    internal val resultUpdate = MutableLiveData<Boolean>()

    internal fun updateTask(task: Task) {
        updateTaskByIdUseCase(UpdateTaskByIdUseCase.Params(task)) {
            it.onSuccess {
                resultUpdate.value = true
            }.onFailure {
                resultUpdate.value = false
            }
        }
    }

}