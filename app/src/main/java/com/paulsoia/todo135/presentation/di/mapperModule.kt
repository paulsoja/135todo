package com.paulsoia.todo135.presentation.di

import com.paulsoia.todo135.data.mapper.TaskMapper
import org.koin.dsl.module

val mapperModule = module {
    factory { TaskMapper() }
}