package com.paulsoia.todo135.presentation.ui.backlog_flow.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulsoia.todo135.business.interactor.SaveTaskUseCase
import com.paulsoia.todo135.business.interactor.SaveTodoTaskUseCase
import com.paulsoia.todo135.business.model.task.Task
import timber.log.Timber

class NewTaskViewModel(
    private val saveTaskUseCase: SaveTaskUseCase,
    private val saveTodoTaskUseCase: SaveTodoTaskUseCase
) : ViewModel() {

    internal val isViewLoading = MutableLiveData<Boolean>()
    internal val warningResult = MutableLiveData<String>()

    internal fun trySaveTask(task: Task): LiveData<Boolean> {
        val saveTaskResult = MutableLiveData<Boolean>()
        when {
            task.message.isEmpty() -> {}
            else -> {
                isViewLoading.value = true
                warningResult.value = ""
                saveTaskUseCase(SaveTaskUseCase.Params(task)) {
                    it.onSuccess {
                        saveTaskResult.value = true
                    }.onFailure {
                        isViewLoading.value = false
                        warningResult.value = it.message
                        saveTaskResult.value = false
                        Timber.w("saveTaskUseCase: ${it.message}")
                    }
                    isViewLoading.value = false
                }
            }
        }
        return saveTaskResult
    }

}