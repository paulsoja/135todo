package com.paulsoia.todo135.presentation.di

import com.paulsoia.todo135.business.interactor.GetAllTasksUseCase
import com.paulsoia.todo135.business.interactor.GetTaskByDateUseCase
import com.paulsoia.todo135.business.interactor.SaveTaskUseCase
import com.paulsoia.todo135.business.interactor.UpdateTaskByIdUseCase
import org.koin.dsl.module

val useCasesModule = module {
    factory { SaveTaskUseCase(get()) }
    factory { GetTaskByDateUseCase(get()) }
    factory { GetAllTasksUseCase(get()) }
    factory { UpdateTaskByIdUseCase(get()) }
}