package com.paulsoia.todo135.presentation.ui.backlog_flow

import android.os.Bundle
import com.paulsoia.todo135.R
import com.paulsoia.todo135.presentation.base.FlowFragment
import com.paulsoia.todo135.presentation.navigation.Screens
import com.paulsoia.todo135.presentation.utils.setLaunchScreen

class BacklogFlowFragment : FlowFragment() {

    companion object {
        fun newInstance() = BacklogFlowFragment()
    }

    override val layoutRes: Int = R.layout.layout_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator.setLaunchScreen(Screens.BacklogScreen)
    }

}