package com.paulsoia.todo135.presentation.di

import com.paulsoia.todo135.business.interactor.*
import org.koin.dsl.module

val useCasesModule = module {
    factory { SaveTaskUseCase(get()) }
    factory { GetTaskByDateUseCase(get()) }
    factory { GetAllTasksUseCase(get()) }
    factory { UpdateTaskByIdUseCase(get()) }
    factory { GetTasksWithDateUseCase(get()) }
    factory { DeleteTaskByIdUseCase(get()) }
    factory { GetAllFilterTasksUseCase(get()) }
    factory { GetAllTagsUseCase(get()) }
    factory { SaveNewTagUseCase(get()) }
    factory { GetTaskByIdUseCase(get()) }
    factory { GetStatsByDatesUseCase(get()) }
    factory { SaveTodoTaskUseCase(get()) }
    factory { UpdateTodoTaskByIdUseCase(get()) }
}