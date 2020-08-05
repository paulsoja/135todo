package com.paulsoia.todo135.presentation.ui.backlog_flow.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulsoia.todo135.business.interactor.GetAllTagsUseCase
import com.paulsoia.todo135.business.interactor.SaveNewTagUseCase
import com.paulsoia.todo135.business.interactor.UpdateTaskByIdUseCase
import com.paulsoia.todo135.business.model.tag.Tag
import com.paulsoia.todo135.business.model.tag.TagMarker
import com.paulsoia.todo135.business.model.task.Task
import timber.log.Timber

class TagViewModel(
    private val getAllTagsUseCase: GetAllTagsUseCase,
    private val saveNewTagUseCase: SaveNewTagUseCase,
    private val updateTaskByIdUseCase: UpdateTaskByIdUseCase
) : ViewModel() {

    internal val message = MutableLiveData<Task>()
    internal val isViewLoading = MutableLiveData<Boolean>()
    internal val warningResult = MutableLiveData<String>()
    internal val result = MutableLiveData<List<TagMarker>>()

    internal fun getAllTags() {
        isViewLoading.value = true
        warningResult.value = ""
        getAllTagsUseCase {
            it.onSuccess {
                result.value = it
            }.onFailure {
                warningResult.value = it.message
                Timber.w("getAllTagsUseCase: ${it.message}")
            }
            isViewLoading.value = false
        }
    }

    internal fun saveNewTag(tag: String, count: Int): LiveData<Boolean> {
        val isSuccess = MutableLiveData<Boolean>()
        val params = SaveNewTagUseCase.Params(Tag(id = null, tag = tag, count = 0))
        saveNewTagUseCase(params) {
            it.onSuccess {
                isSuccess.value = true
            }.onFailure {
                isSuccess.value = false
            }
        }
        return isSuccess
    }

    internal fun updateTask(task: Task): LiveData<Boolean> {
        val isSuccess = MutableLiveData<Boolean>()
        val params = UpdateTaskByIdUseCase.Params(task)
        updateTaskByIdUseCase(params) {
            it.onSuccess {
                isSuccess.value = true
            }.onFailure {
                isSuccess.value = false
            }
        }
        return isSuccess
    }

}