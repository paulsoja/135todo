package com.paulsoia.todo135

import android.app.Application
import com.paulsoia.todo135.presentation.di.*
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import timber.log.Timber

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    private lateinit var cicerone: Cicerone<Router>

    val navigatorHolder: NavigatorHolder
        get() = cicerone.navigatorHolder

    val router: Router
        get() = cicerone.router

    override fun onCreate() {
        instance = this
        super.onCreate()
        configureDI()
        cicerone = Cicerone.create()
    }

    private fun configureDI() = startKoin {
        androidContext(this@App)
        modules(
            listOf(
                viewModelModule,
                appModule,
                repositoriesModule,
                mapperModule,
                useCasesModule,
                sharedPreferencesModule, preferencesRepositoryModule
            )
        )

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

}