package com.paulsoia.todo135.presentation.ui.backlog_flow.backlog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulsoia.todo135.business.interactor.GetAllTasksUseCase
import com.paulsoia.todo135.business.interactor.UpdateTaskByIdUseCase
import com.paulsoia.todo135.business.model.task.Task

class BacklogViewModel(
    private val getAllTasks: GetAllTasksUseCase,
    private val updateTaskByIdUseCase: UpdateTaskByIdUseCase
) : ViewModel() {

    internal val isViewLoading = MutableLiveData<Boolean>()
    internal val warningResult = MutableLiveData<String>()
    internal val result = MutableLiveData<List<Task>>()
    internal val resultUpdate = MutableLiveData<Boolean>()

    internal fun getAllTasks(): LiveData<List<Task>> {
        getAllTasks{
            isViewLoading.value = true
            it.onSuccess {
                result.value = it
            }.onFailure {
                warningResult.value = it.message
            }
            isViewLoading.value = false
        }
        return result
    }

    internal fun updateTask(task: Task) {
        updateTaskByIdUseCase(UpdateTaskByIdUseCase.Params(task)) {
            isViewLoading.value = true
            it.onSuccess {
                resultUpdate.value = true
            }.onFailure {
                resultUpdate.value = false
            }
            isViewLoading.value = false
        }
    }

}