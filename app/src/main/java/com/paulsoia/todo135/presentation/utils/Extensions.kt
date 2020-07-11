package com.paulsoia.todo135.presentation.utils

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.paulsoia.todo135.App
import com.paulsoia.todo135.business.model.base.TypeEnum
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Forward
import ru.terrakok.cicerone.commands.Replace

fun View.onClick(call: (v: View) -> Unit) {
    setOnClickListener {
        call.invoke(it)
    }
}

inline fun <reified T : Enum<T>> getEnumTypeValue(type: String): T? {
    val values = enumValues<T>()
    return values.firstOrNull {
        it is TypeEnum && (it as TypeEnum).type.equals(type, true)
    }
}

fun BottomSheetBehavior<View>.showBottomSheet(show: Boolean = true) {
    state = if (show) BottomSheetBehavior.STATE_EXPANDED else BottomSheetBehavior.STATE_COLLAPSED
}

fun Navigator.setLaunchScreen(vararg screen: SupportAppScreen) { // can we use `newRootScreen` instead of `setLaunchScreen`?
    applyCommands(
        Array<Command>(screen.size + 1) { pos ->
            when (pos) {
                0 -> BackTo(null)
                1 -> Replace(screen[pos - 1])
                else -> Forward(screen[pos - 1])
            }
        }
    )
}

fun navigate(): Router {
    return App.instance.router
}