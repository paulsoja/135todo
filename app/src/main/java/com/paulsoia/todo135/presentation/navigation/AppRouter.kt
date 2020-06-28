package com.paulsoia.todo135.presentation.navigation

import ru.terrakok.cicerone.Router

class AppRouter : Router() {

    fun startMainFlow() {
        newRootScreen(Screens.TodoFlowScreen)
    }

}