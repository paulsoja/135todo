package com.paulsoia.todo135.presentation.ui.settings_flow.settings.about_app

import com.paulsoia.todo135.R
import com.paulsoia.todo135.presentation.base.BaseBottomSheetDialogFragment

class AboutAppDialog : BaseBottomSheetDialogFragment() {

    companion object {
        fun newInstance() = AboutAppDialog()
    }

    override val resLayout = R.layout.dialog_about_app

}