package com.paulsoia.todo135.presentation.di

import com.paulsoia.todo135.presentation.ui.backlog_flow.backlog.BacklogViewModel
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.CreateIncomingTaskViewModel
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.EditTaskViewModel
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.MenuViewModel
import com.paulsoia.todo135.presentation.ui.todo_flow.todo.TodoViewModel
import com.paulsoia.todo135.presentation.ui.todo_flow.days.NewTaskTodoViewModel
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.TagViewModel
import com.paulsoia.todo135.presentation.ui.stats_flow.stats.StatsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { TodoViewModel(get(), get(), get(), get(), get()) }
    viewModel { NewTaskTodoViewModel(get(), get()) }
    viewModel { CreateIncomingTaskViewModel(get(), get()) }
    viewModel { BacklogViewModel(get(), get(), get(), get()) }
    viewModel { EditTaskViewModel(get()) }
    viewModel { MenuViewModel(get(), get()) }
    viewModel { TagViewModel(get(), get(), get()) }
    viewModel { StatsViewModel(get(), get()) }
}