package com.paulsoia.todo135.presentation.di

import com.paulsoia.todo135.business.repository.TaskRepository
import com.paulsoia.todo135.business.repository.TaskTodoRepository
import com.paulsoia.todo135.data.repository.TaskDataRepository
import com.paulsoia.todo135.data.repository.TaskTodoDataRepository
import org.koin.dsl.module

val repositoriesModule = module {
    factory<TaskRepository> { TaskDataRepository(get(), get(), get(), get()) }
    factory<TaskTodoRepository> { TaskTodoDataRepository() }
}