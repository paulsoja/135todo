package com.paulsoia.todo135.presentation.navigation

import com.paulsoia.todo135.presentation.base.AppFragment
import com.paulsoia.todo135.presentation.ui.backlog_flow.BacklogFlowFragment
import com.paulsoia.todo135.presentation.ui.backlog_flow.backlog.BacklogFragment
import com.paulsoia.todo135.presentation.ui.settings_flow.SettingsFlowFragment
import com.paulsoia.todo135.presentation.ui.settings_flow.settings.SettingsFragment
import com.paulsoia.todo135.presentation.ui.stats_flow.StatsFlowFragment
import com.paulsoia.todo135.presentation.ui.stats_flow.stats.StatsFragment
import com.paulsoia.todo135.presentation.ui.todo_flow.TodoFlowFragment
import com.paulsoia.todo135.presentation.ui.todo_flow.todo.TodoFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object AppScreen : SupportAppScreen() {
        override fun getFragment() = AppFragment.newInstance()
    }

    object TodoFlowScreen : SupportAppScreen() {
        override fun getFragment() = TodoFlowFragment.newInstance()
    }

    object TodoScreen : SupportAppScreen() {
        override fun getFragment() = TodoFragment.newInstance()
    }

    object BacklogFlowScreen : SupportAppScreen() {
        override fun getFragment() = BacklogFlowFragment.newInstance()
    }

    object BacklogScreen : SupportAppScreen() {
        override fun getFragment() = BacklogFragment.newInstance()
    }

    object SettingsFlowScreen : SupportAppScreen() {
        override fun getFragment() = SettingsFlowFragment.newInstance()
    }

    object SettingsScreen : SupportAppScreen() {
        override fun getFragment() = SettingsFragment.newInstance()
    }

    object StatsFlowScreen : SupportAppScreen() {
        override fun getFragment() = StatsFlowFragment.newInstance()
    }

    object StatsScreen : SupportAppScreen() {
        override fun getFragment() = StatsFragment.newInstance()
    }

}