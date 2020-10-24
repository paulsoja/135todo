package com.paulsoia.todo135.business.interactor

import com.paulsoia.todo135.business.interactor.base.UseCase
import com.paulsoia.todo135.business.model.tag.TagMarker
import com.paulsoia.todo135.business.repository.TaskRepository
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