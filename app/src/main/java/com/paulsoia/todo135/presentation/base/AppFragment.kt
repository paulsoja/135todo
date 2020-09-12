package com.paulsoia.todo135.presentation.base

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.paulsoia.todo135.App
import com.paulsoia.todo135.R
import com.paulsoia.todo135.presentation.navigation.Screens
import com.paulsoia.todo135.presentation.utils.navigate
import com.paulsoia.todo135.presentation.utils.setLaunchScreen
import kotlinx.android.synthetic.main.fragment_main.*
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.Command

class AppFragment : BaseFragment() {

    companion object {
        fun newInstance() = AppFragment()
    }

    override val layoutRes: Int = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator.setLaunchScreen(Screens.TodoFlowScreen)
        setupBottomBar()
    }

    private fun setupBottomBar() {
        with(bottomBar) {
            setOnNavigationItemSelectedListener { item: MenuItem ->
                return@setOnNavigationItemSelectedListener when (item.itemId) {
                    R.id.nav_todo -> {
                        navigate().newRootScreen(Screens.TodoFlowScreen)
                        true
                    }
                    R.id.nav_backlog -> {
                        navigate().newRootScreen(Screens.BacklogFlowScreen)
                        true
                    }
                    R.id.nav_stats -> {
                        navigate().newRootScreen(Screens.StatsFlowScreen)
                        true
                    }
                    R.id.nav_menu -> {
                        navigate().newRootScreen(Screens.SettingsFlowScreen)
                        true
                    }
                    else -> true
                }
            }
        }
    }

    protected val navigator: Navigator by lazy {
        object : SupportAppNavigator(requireActivity(), childFragmentManager, R.id.root) {
            override fun backToUnexisting(screen: @NotNull SupportAppScreen) {
                super.backToUnexisting(screen)
                navigator.setLaunchScreen(screen)
            }

            override fun setupFragmentTransaction(
                command: @NotNull Command,
                currentFragment: @Nullable Fragment?,
                nextFragment: @Nullable Fragment?,
                fragmentTransaction: @NotNull FragmentTransaction
            ) {
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left)
                fragmentTransaction.setReorderingAllowed(true)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        App.instance.navigatorHolder.removeNavigator()
        super.onPause()
    }

}