package com.paulsoia.todo135.presentation.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.paulsoia.todo135.App
import com.paulsoia.todo135.R
import com.paulsoia.todo135.presentation.navigation.Screens
import com.paulsoia.todo135.presentation.utils.navigate
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    //private val navigator = SupportAppNavigator(this, supportFragmentManager, R.id.fragment_container)

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.fragment_container) as? BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coldStart()
        /*var fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment == null) {
            fragment = TodoFlowFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }*/
    }

    private fun coldStart() {
        navigate().newRootScreen(Screens.AppScreen)
    }

    val navigator: Navigator =
        object : SupportAppNavigator(this, supportFragmentManager, R.id.fragment_container) {
            override fun setupFragmentTransaction(
                command: @NotNull Command,
                currentFragment: @Nullable Fragment?,
                nextFragment: @Nullable Fragment?,
                fragmentTransaction: @NotNull FragmentTransaction
            ) {
                fragmentTransaction.setReorderingAllowed(true)
            }
        }

    override fun onResume() {
        super.onResume()
        App.instance.navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        App.instance.navigatorHolder.removeNavigator()
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }

}