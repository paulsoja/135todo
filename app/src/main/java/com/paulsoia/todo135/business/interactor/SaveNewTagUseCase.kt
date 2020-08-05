package com.paulsoia.todo135.business.interactor

import com.paulsoia.todo135.business.model.tag.Tag
import com.paulsoia.todo135.business.repository.TaskRepository
import global.zakaz.stockman.domain.interactor.base.UseCase

class SaveNewTagUseCase(
    private val repository: TaskRepository
) : UseCase<SaveNewTagUseCase.Params, Any>() {

    data class Params(val tag: Tag)

    override suspend fun run(): Result<Any> {
        if (params == null) throw IllegalArgumentException("Parameter required")
        return try {
            params!!.run {
                repository.getAllTags().onSuccess {
                    //if this tag is exist we update this tag and increase count
                    //else we create new tag
                    it.find { it.tag == tag.tag }?.let {
                        repository.updateTag(it.also { it.count = it.count + 1 })
                    } ?: run {
                        repository.saveTag(tag)
                    }
                }
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}