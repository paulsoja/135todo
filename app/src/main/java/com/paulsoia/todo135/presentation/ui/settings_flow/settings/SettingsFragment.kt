package com.paulsoia.todo135.presentation.ui.settings_flow.settings

import android.os.Bundle
import android.view.View
import com.paulsoia.todo135.R
import com.paulsoia.todo135.presentation.base.BaseFragment
import com.paulsoia.todo135.presentation.ui.settings_flow.settings.name.NameDialog
import com.paulsoia.todo135.presentation.ui.settings_flow.settings.theme.ThemeDialog
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : BaseFragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override val layoutRes = R.layout.fragment_settings

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        holderName.setOnClickListener {
            NameDialog.newInstance().apply {
                setTargetFragment(this@SettingsFragment, 0)
            }.show(parentFragmentManager, "name")
        }
        holderStats.setOnClickListener { }
        holderReminder.setOnClickListener { }
        holderWidget.setOnClickListener { }
        holderTheme.setOnClickListener {
            ThemeDialog.newInstance().apply {
                setTargetFragment(this@SettingsFragment, 0)
            }.show(parentFragmentManager, "theme")
        }
        holderAboutMethodology.setOnClickListener { }
        holderAboutApp.setOnClickListener { }
        holderLogout.setOnClickListener { }
    }

}