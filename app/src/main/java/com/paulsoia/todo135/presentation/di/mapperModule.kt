package com.paulsoia.todo135.presentation.di

import com.paulsoia.todo135.data.mapper.TagMapper
import com.paulsoia.todo135.data.mapper.TaskMapper
import com.paulsoia.todo135.data.mapper.TaskTodoMapper
import org.koin.dsl.module

val mapperModule = module {
    factory { TaskMapper() }
    factory { TaskTodoMapper() }
    factory { TagMapper() }
}