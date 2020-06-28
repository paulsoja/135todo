package com.paulsoia.todo135.presentation.di

import com.paulsoia.todo135.business.interactor.GetTaskByDateUseCase
import com.paulsoia.todo135.business.interactor.SaveTaskUseCase
import org.koin.dsl.module

val useCasesModule = module {
    factory { SaveTaskUseCase(get()) }
    factory { GetTaskByDateUseCase(get()) }
}