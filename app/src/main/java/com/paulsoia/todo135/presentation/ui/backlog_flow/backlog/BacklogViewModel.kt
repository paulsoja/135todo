package com.paulsoia.todo135.presentation.ui.backlog_flow.backlog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulsoia.todo135.business.interactor.GetAllTasksUseCase
import com.paulsoia.todo135.business.model.task.Task

class BacklogViewModel(
    private val getAllTasks: GetAllTasksUseCase
) : ViewModel() {

    internal val isViewLoading = MutableLiveData<Boolean>()
    internal val warningResult = MutableLiveData<String>()
    internal val result = MutableLiveData<List<Task>>()

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

}