package com.paulsoia.todo135.presentation.ui.settings_flow.settings.theme

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.setting.ThemeBaseType
import com.paulsoia.todo135.business.model.setting.ThemeColor
import com.paulsoia.todo135.presentation.base.BaseBottomSheetDialogFragment
import com.paulsoia.todo135.presentation.base.MainActivity
import com.paulsoia.todo135.presentation.ui.settings_flow.settings.SettingsFragment
import com.paulsoia.todo135.presentation.ui.settings_flow.settings.theme.items.ThemeAdapter
import kotlinx.android.synthetic.main.dialog_theme.*

class ThemeDialog : BaseBottomSheetDialogFragment(), ThemeAdapter.ThemeChangeListener {

    companion object {
        fun newInstance() = ThemeDialog()
    }



    override val resLayout = R.layout.dialog_theme

    private val adapter = ThemeAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.callback = this
        initRecyclerView()
        adapter.swapData(mutableListOf<ThemeColor>().apply {
            //TODO this is stub data
            add(ThemeColor(ThemeBaseType.GREEN, "green", false))
            add(ThemeColor(ThemeBaseType.BLUE, "blue", false))
            add(ThemeColor(ThemeBaseType.INDIGO, "indigo", false))
        })
    }

    private fun initRecyclerView() {
        with(rvTheme) {
            layoutManager = LinearLayoutManager(requireContext())
            val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            addItemDecoration(divider)
            adapter = this@ThemeDialog.adapter
        }
    }

    override fun onColorClicked(theme: ThemeColor) {
        //save theme to prefs
        (activity as? MainActivity)?.updateTheme(theme.color)
    }

}