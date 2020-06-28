package com.paulsoia.todo135.presentation.di

import com.paulsoia.todo135.business.repository.TaskRepository
import com.paulsoia.todo135.data.repository.TaskDataRepository
import org.koin.dsl.module

val repositoriesModule = module {
    factory<TaskRepository> { TaskDataRepository(get(), get()) }
}