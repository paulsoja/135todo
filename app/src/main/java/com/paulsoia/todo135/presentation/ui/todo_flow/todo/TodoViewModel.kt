package com.paulsoia.todo135.presentation.ui.todo_flow.todo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulsoia.todo135.business.interactor.*
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.model.task.TaskMarker

class TodoViewModel(
    private val updateTaskByIdUseCase: UpdateTaskByIdUseCase,
    private val updateTodoTaskByIdUseCase: UpdateTodoTaskByIdUseCase,
    private val saveTaskUseCase: SaveTaskUseCase,
    private val getTasksWithDateUseCase: GetTasksWithDateUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val saveTodoTaskUseCase: SaveTodoTaskUseCase,
) : ViewModel() {

    internal val isViewLoading = MutableLiveData<Boolean>()
    internal val warningResult = MutableLiveData<String>()
    internal val resultSaveTask = MutableLiveData<Boolean>()
    internal val resultTasks = MutableLiveData<Triple<List<TaskMarker>, List<TaskMarker>, List<TaskMarker>>>()
    private val resultUpdate = MutableLiveData<Boolean>()
    internal val tasks = MutableLiveData<List<Task>>()

    internal fun saveTask(task: Task) {
        isViewLoading.value = true
        saveTaskUseCase(SaveTaskUseCase.Params(task)) {
            it.onSuccess {
                resultSaveTask.postValue(true)
            }.onFailure {
                resultSaveTask.postValue(false)
                warningResult.postValue(it.message)
            }
            isViewLoading.postValue(false)
        }
    }

    internal fun saveTodoTask(task: Task) {
        isViewLoading.value = true
        saveTodoTaskUseCase(SaveTodoTaskUseCase.Params(task)) {
            it.onSuccess {
                resultSaveTask.value = true
            }.onFailure {
                resultSaveTask.value = false
                warningResult.value = it.message
            }
            isViewLoading.value = false
        }
    }

    internal fun getAllTasks() {
        getAllTasksUseCase {
            it.onSuccess {
                tasks.value = it
            }.onFailure {
                val trtr = it
            }
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

    internal fun updateTask(task: Task) {
        updateTodoTaskByIdUseCase(UpdateTodoTaskByIdUseCase.Params(task)) {
            it.onSuccess {
                resultUpdate.value = true
            }.onFailure {
                resultUpdate.value = false
            }
        }
    }

}