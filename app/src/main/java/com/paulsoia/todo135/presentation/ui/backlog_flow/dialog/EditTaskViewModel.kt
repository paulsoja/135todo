package com.paulsoia.todo135.presentation.ui.backlog_flow.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulsoia.todo135.business.interactor.DeleteTaskByIdUseCase
import com.paulsoia.todo135.business.interactor.DeleteTodoTaskByIdUseCase
import com.paulsoia.todo135.business.interactor.UpdateTaskByIdUseCase
import com.paulsoia.todo135.business.interactor.UpdateTodoTaskByIdUseCase
import com.paulsoia.todo135.business.model.ScreenType
import com.paulsoia.todo135.business.model.task.Task
import timber.log.Timber

class EditTaskViewModel(
    private val updateTaskByIdUseCase: UpdateTaskByIdUseCase,
    private val updateTodoTaskByIdUseCase: UpdateTodoTaskByIdUseCase,
    private val deleteTaskByIdUseCase: DeleteTaskByIdUseCase,
    private val deleteTodoTaskByIdUseCase: DeleteTodoTaskByIdUseCase,
) : ViewModel() {

    internal val message = MutableLiveData<Task>()
    internal var screenType: ScreenType? = null

    internal val isViewLoading = MutableLiveData<Boolean>()
    internal val warningResult = MutableLiveData<String>()
    internal val deleteTaskResult = MutableLiveData<Boolean>()

    internal fun tryUpdateTask(task: Task): LiveData<Boolean> {
        val updateTaskResult = MutableLiveData<Boolean>()
        when {
            task.message.isEmpty() -> {}
            //task.level == LevelType.NONE -> {}
            else -> {
                isViewLoading.value = true
                warningResult.value = ""
                when(screenType) {
                    ScreenType.TODO_SCREEN -> {
                        updateTodoTaskByIdUseCase(UpdateTodoTaskByIdUseCase.Params(task)) {
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
                    ScreenType.BACKLOG_SCREEN -> {
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

            }
        }
        return updateTaskResult
    }

    internal fun deleteTask(task: Task) {
        when(screenType) {
            ScreenType.TODO_SCREEN -> {
                val params = task.id?.let { DeleteTodoTaskByIdUseCase.Params(taskId = it) }
                params?.let {
                    isViewLoading.value = true
                    deleteTodoTaskByIdUseCase(it) {
                        it.onSuccess {
                            deleteTaskResult.value = true
                        }.onFailure {
                            deleteTaskResult.value = false
                            warningResult.value = it.message
                            Timber.w("deleteTodoTaskByIdUseCase: ${it.message}")
                        }
                        isViewLoading.value = false
                    }
                }
            }
            ScreenType.BACKLOG_SCREEN -> {
                val params = task.id?.let { DeleteTaskByIdUseCase.Params(taskId = it) }
                params?.let {
                    isViewLoading.value = true
                    deleteTaskByIdUseCase(it) {
                        it.onSuccess {
                            deleteTaskResult.value = true
                        }.onFailure {
                            deleteTaskResult.value = false
                            warningResult.value = it.message
                            Timber.w("deleteTaskByIdUseCase: ${it.message}")
                        }
                        isViewLoading.value = false
                    }
                }
            }
        }
    }

}