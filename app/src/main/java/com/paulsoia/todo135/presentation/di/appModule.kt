package com.paulsoia.todo135.presentation.di

import androidx.room.Room
import com.paulsoia.todo135.data.database.AppDatabase
import org.koin.dsl.module

val appModule = module {
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "todo135_db").build() }

    single { get<AppDatabase>().getTasksDao() }
    single { get<AppDatabase>().getTagsDao() }
}