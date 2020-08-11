package com.paulsoia.todo135.presentation.ui.backlog_flow.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulsoia.todo135.business.interactor.DeleteTaskByIdUseCase
import com.paulsoia.todo135.business.interactor.UpdateTaskByIdUseCase
import com.paulsoia.todo135.business.model.task.LevelType
import com.paulsoia.todo135.business.model.task.Task
import timber.log.Timber

class MenuViewModel(
    private val updateTaskByIdUseCase: UpdateTaskByIdUseCase,
    private val deleteTaskByIdUseCase: DeleteTaskByIdUseCase
) : ViewModel() {

    internal val message = MutableLiveData<Task>()

    internal val isViewLoading = MutableLiveData<Boolean>()
    internal val warningResult = MutableLiveData<String>()
    val updateTaskResult = MutableLiveData<Boolean>()

    internal fun tryUpdateTask(task: Task): LiveData<Boolean> {
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
                        isViewLoading.value = false
                        warningResult.value = it.message
                        updateTaskResult.value = false
                        Timber.w("saveTaskUseCase: ${it.message}")
                    }
                    isViewLoading.value = false
                }
            }
        }
        return updateTaskResult
    }

    internal fun deleteTaskById(taskId: Long): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        deleteTaskByIdUseCase(DeleteTaskByIdUseCase.Params(taskId)) {
            it.onSuccess {
                result.value = true
            }.onFailure {
                result.value = false
            }
        }
        return result
    }

}