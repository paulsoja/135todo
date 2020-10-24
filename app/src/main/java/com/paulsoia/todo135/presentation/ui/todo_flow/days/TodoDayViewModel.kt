package com.paulsoia.todo135.presentation.ui.todo_flow.days

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulsoia.todo135.business.interactor.GetAllTasksUseCase
import com.paulsoia.todo135.business.interactor.GetTaskByIdUseCase
import com.paulsoia.todo135.business.interactor.UpdateTaskByIdUseCase
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.model.task.TaskMarker

class TodoDayViewModel(
    private val updateTaskByIdUseCase: UpdateTaskByIdUseCase,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase
) : ViewModel() {

    internal val items = MutableLiveData<List<TaskMarker>>()
    private val resultUpdate = MutableLiveData<Boolean>()
    internal val tasks = MutableLiveData<List<Task>>()

    internal fun getAllTasks() {
        getAllTasksUseCase {
            it.onSuccess {
                tasks.value = it
            }.onFailure {
                val trtr = it
            }
        }
    }

    internal fun updateTask(task: Task) {
        updateTaskByIdUseCase(UpdateTaskByIdUseCase.Params(task)) {
            it.onSuccess {
                resultUpdate.value = true
            }.onFailure {
                resultUpdate.value = false
            }
        }
    }

    internal fun getTaskById(task: Task): LiveData<Task> {
        val taskLiveData = MutableLiveData<Task>()
        getTaskByIdUseCase(GetTaskByIdUseCase.Params(task.id)) { res ->
            res.onSuccess { task ->
                taskLiveData.value = task
            }.onFailure {

            }
        }
        return taskLiveData
    }

}