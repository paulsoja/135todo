package com.paulsoia.todo135.presentation.ui.settings_flow.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.repository.PrefRepository
import com.paulsoia.todo135.presentation.base.BaseFragment
import com.paulsoia.todo135.presentation.ui.settings_flow.settings.about_app.AboutAppDialog
import com.paulsoia.todo135.presentation.ui.settings_flow.settings.name.NameDialog
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.ext.android.inject


class SettingsFragment : BaseFragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    override val layoutRes = R.layout.fragment_settings

    private val prefRepository: PrefRepository by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    private fun initViews() {
        prefRepository.getName()?.let {
            if (it.isNotEmpty()) tvNameDesc.text = it
        }
    }

    private fun initListeners() {
        holderName.setOnClickListener {
            NameDialog.newInstance().apply {
                setTargetFragment(this@SettingsFragment, 0)
            }.show(parentFragmentManager, "name")
        }
        //for next release
        //holderStats.setOnClickListener { }
        //holderReminder.setOnClickListener { }
        //holderWidget.setOnClickListener { }
        /*holderTheme.setOnClickListener {
            ThemeDialog.newInstance().apply {
                setTargetFragment(this@SettingsFragment, 0)
            }.show(parentFragmentManager, "theme")
        }*/
        holderAboutMethodology.setOnClickListener {
            val url = "https://www.themuse.com/advice/a-better-todo-list-the-135-rule"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
        holderAboutApp.setOnClickListener {
            fragmentManager?.let {
                AboutAppDialog.newInstance().apply {
                    setTargetFragment(this@SettingsFragment, 0)
                }.show(it, "about")
            }
        }
        holderLogout.setOnClickListener { }
    }

}