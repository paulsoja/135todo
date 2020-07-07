package com.paulsoia.todo135.presentation.di

import com.paulsoia.todo135.presentation.ui.backlog_flow.backlog.BacklogViewModel
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.EditTaskViewModel
import com.paulsoia.todo135.presentation.ui.todo_flow.todo.TodoViewModel
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.NewTaskViewModel
import com.paulsoia.todo135.presentation.ui.todo_flow.todo.items.TodoItemViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TodoViewModel(get()) }
    viewModel { NewTaskViewModel(get()) }
    viewModel { TodoItemViewModel(get()) }
    viewModel { BacklogViewModel(get(), get()) }
    viewModel { EditTaskViewModel(get()) }
}