package com.paulsoia.todo135.presentation.ui.settings_flow.settings.name

import android.os.Bundle
import android.view.View
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.repository.PrefRepository
import com.paulsoia.todo135.presentation.base.BaseBottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_name.*
import org.koin.android.ext.android.inject

class NameDialog : BaseBottomSheetDialogFragment() {

    companion object {
        fun newInstance() = NameDialog()
    }

    private val prefRepository: PrefRepository by inject()

    override val resLayout = R.layout.dialog_name

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etName.requestFocus()
        prefRepository.getName()?.let {
            if (it.isNotEmpty()) etName.setText(it)
        }
        btnSave.setOnClickListener {
            val name = etName.text.toString()
            prefRepository.saveName(name)
            dismiss()
        }
    }

}