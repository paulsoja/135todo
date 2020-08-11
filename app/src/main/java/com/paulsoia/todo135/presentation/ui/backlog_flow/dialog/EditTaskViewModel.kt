package com.paulsoia.todo135.presentation.ui.backlog_flow.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulsoia.todo135.business.interactor.UpdateTaskByIdUseCase
import com.paulsoia.todo135.business.model.task.LevelType
import com.paulsoia.todo135.business.model.task.Task
import timber.log.Timber

class EditTaskViewModel(
    private val updateTaskByIdUseCase: UpdateTaskByIdUseCase
) : ViewModel() {

    internal val message = MutableLiveData<Task>()

    internal val isViewLoading = MutableLiveData<Boolean>()
    internal val warningResult = MutableLiveData<String>()

    internal fun tryUpdateTask(task: Task): LiveData<Boolean> {
        val updateTaskResult = MutableLiveData<Boolean>()
        when {
            task.message.isEmpty() -> {}
            task.level == LevelType.NONE -> {}
            else -> {
                isViewLoading.value = true
                warningResult.value = ""
                updateTaskByIdUseCase(UpdateTaskByIdUseCase.Params(task)) {
                    it.onSuccess {
                        updateTaskResult.value = true
                    }.onFailure {
                        warningResult.value = it.message
                        updateTaskResult.value = false
                        Timber.w("updateTaskByIdUseCase: ${it.message}")
                    }
                    isViewLoading.value = false
                }
            }
        }
        return updateTaskResult
    }

}