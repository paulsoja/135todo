package com.paulsoia.todo135.presentation.di

import android.content.Context
import com.paulsoia.todo135.business.repository.PrefRepository
import com.paulsoia.todo135.data.repository.PrefDataRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sharedPreferencesModule = module {
    factory {
        androidContext().run { getSharedPreferences(packageName + "_prefs", Context.MODE_PRIVATE) }
    }
}

val preferencesRepositoryModule = module {
    factory<PrefRepository> {
        PrefDataRepository(get())
    }
}