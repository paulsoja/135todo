package com.paulsoia.todo135.presentation.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.paulsoia.todo135.App
import com.paulsoia.todo135.R
import com.paulsoia.todo135.presentation.utils.setLaunchScreen
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.Command

abstract class FlowFragment : BaseFragment() {

    override val layoutRes: Int = R.layout.layout_container

    private val currentFragment
        get() = childFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    protected val navigator: Navigator by lazy {
        object : SupportAppNavigator(requireActivity(), childFragmentManager, R.id.container) {
            override fun backToUnexisting(screen: @NotNull SupportAppScreen) {
                super.backToUnexisting(screen)
                navigator.setLaunchScreen(screen)
            }

            override fun activityBack() {
                onExit()
            }

            override fun setupFragmentTransaction(
                command: @NotNull Command,
                currentFragment: @Nullable Fragment?,
                nextFragment: @Nullable Fragment?,
                fragmentTransaction: @NotNull FragmentTransaction
            ) {
                // Fix incorrect order lifecycle categoryCategoryCallback of MainTabsFlowFragment
                fragmentTransaction.setReorderingAllowed(true)
            }
        }
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: onExit()
    }

    open fun onExit() {}

    override fun onResume() {
        super.onResume()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        App.instance.navigatorHolder.removeNavigator()
        super.onPause()
    }

}