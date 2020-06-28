package com.paulsoia.todo135.presentation.di

import com.paulsoia.todo135.presentation.ui.todo_flow.todo.TodoViewModel
import com.paulsoia.todo135.presentation.ui.todo_flow.todo.dialog.TaskViewModel
import com.paulsoia.todo135.presentation.ui.todo_flow.todo.items.TodoItemViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TodoViewModel(get()) }
    viewModel { TaskViewModel(get()) }
    viewModel { TodoItemViewModel(get()) }
}