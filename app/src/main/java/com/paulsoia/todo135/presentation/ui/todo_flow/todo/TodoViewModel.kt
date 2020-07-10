package com.paulsoia.todo135.presentation.ui.todo_flow.todo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulsoia.todo135.business.interactor.GetTasksWithDateUseCase
import com.paulsoia.todo135.business.interactor.SaveTaskUseCase
import com.paulsoia.todo135.business.model.task.Task

class TodoViewModel(
    private val saveTaskUseCase: SaveTaskUseCase,
    private val getTasksWithDateUseCase: GetTasksWithDateUseCase
) : ViewModel() {

    internal val isViewLoading = MutableLiveData<Boolean>()
    internal val warningResult = MutableLiveData<String>()
    internal val resultSaveTask = MutableLiveData<Boolean>()
    internal val resultTasks = MutableLiveData<List<Task>>()

    internal fun saveTask(task: Task) {
        isViewLoading.value = true
        saveTaskUseCase(SaveTaskUseCase.Params(task)) {
            it.onSuccess {
                resultSaveTask.value = true
            }.onFailure {
                resultSaveTask.value = false
                warningResult.value = it.message
            }
            isViewLoading.value = false
        }
    }

    internal fun getTaskWithDate() {
        isViewLoading.value = true
        getTasksWithDateUseCase {
            it.onSuccess {
                resultTasks.value = it
            }.onFailure {
                warningResult.value = it.message
            }
            isViewLoading.value = false
        }
    }

}