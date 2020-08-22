package com.paulsoia.todo135.presentation.base

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.paulsoia.todo135.App
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.setting.ThemeBaseType
import com.paulsoia.todo135.presentation.navigation.Screens
import com.paulsoia.todo135.presentation.utils.navigate
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val currentFragment: BaseFragment?
        get() = supportFragmentManager.findFragmentById(R.id.fragment_container) as? BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        coldStart()
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

    fun updateTheme(color: ThemeBaseType) {
        when(color) {
            ThemeBaseType.BLUE -> {
                setTheme(R.style.BlueAppTheme)
                window.statusBarColor = resources.getColor(R.color.main_blue_light)
            }
            ThemeBaseType.INDIGO -> {
                setTheme(R.style.IndigoAppTheme)
                window.statusBarColor = resources.getColor(R.color.main_indigo_light)
            }
            else -> {
                setTheme(R.style.BlueAppTheme)
                window.statusBarColor = resources.getColor(R.color.main_blue_light)
            }
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        //recreateActivity()
    }

    private fun recreateActivity() {
        val intent = intent
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
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