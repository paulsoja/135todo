package com.paulsoia.todo135.business.interactor

import com.paulsoia.todo135.business.model.tag.TagMarker
import com.paulsoia.todo135.business.repository.TaskRepository
import global.zakaz.stockman.domain.interactor.base.UseCase
import java.lang.Exception

class GetAllTagsUseCase(
    private val repository: TaskRepository
) : UseCase<UseCase.None, List<TagMarker>>() {

    override suspend fun run(): Result<List<TagMarker>> {
        return try {
            repository.getAllTags()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}