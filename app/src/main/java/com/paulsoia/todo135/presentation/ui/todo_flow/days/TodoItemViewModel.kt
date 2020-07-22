package com.paulsoia.todo135.presentation.ui.todo_flow.days

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulsoia.todo135.business.interactor.GetTaskByDateUseCase
import com.paulsoia.todo135.business.model.task.Task

class TodoItemViewModel(
    private val getTaskByDateUseCase: GetTaskByDateUseCase
) : ViewModel() {

    internal val isViewLoading = MutableLiveData<Boolean>()
    internal val warningResult = MutableLiveData<String>()
    internal val result = MutableLiveData<List<Task>>()

    /*internal fun getTaskByDate(date: String): LiveData<List<Task>> {
        getTaskByDateUseCase(GetTaskByDateUseCase.Params(date)) {
            isViewLoading.value = true
            it.onSuccess {
                result.value = it
            }.onFailure {
                warningResult.value = it.message
            }
            isViewLoading.value = false
        }
        return result
    }*/

}