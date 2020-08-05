package com.paulsoia.todo135.presentation.ui.backlog_flow.backlog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulsoia.todo135.business.interactor.DeleteTaskByIdUseCase
import com.paulsoia.todo135.business.interactor.GetAllFilterTasksUseCase
import com.paulsoia.todo135.business.interactor.UpdateTaskByIdUseCase
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.model.task.TaskMarker
import com.paulsoia.todo135.business.repository.PrefRepository

class BacklogViewModel(
    private val getAllTasks: GetAllFilterTasksUseCase,
    private val updateTaskByIdUseCase: UpdateTaskByIdUseCase,
    private val deleteTaskByIdUseCase: DeleteTaskByIdUseCase,
    private val prefRepository: PrefRepository
) : ViewModel() {

    internal val isViewLoading = MutableLiveData<Boolean>()
    internal val warningResult = MutableLiveData<String>()
    internal val result = MutableLiveData<List<TaskMarker>>()
    internal val resultUpdate = MutableLiveData<Boolean>()

    internal fun getAllTasks(): LiveData<List<TaskMarker>> {
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

    internal fun deleteTask(task: Task) {
        if (task.id != null) {
            deleteTaskByIdUseCase(DeleteTaskByIdUseCase.Params(taskId = task.id)) {
                it.onSuccess {
                    resultUpdate.value = true
                }.onFailure {
                    resultUpdate.value = false
                }
            }
        }
    }

    internal fun saveFilterType(type: String) = prefRepository.setHowToFilterTask(type)

    internal fun getFilterType() = prefRepository.getHowToFilterTask() ?: "all"

}