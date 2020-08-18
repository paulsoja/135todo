package com.paulsoia.todo135.presentation.ui.stats_flow.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import com.paulsoia.todo135.presentation.base.BaseBottomSheetDialogFragment

import java.util.Calendar

class DateDialog : BaseBottomSheetDialogFragment() {

    companion object {
        fun newInstance() = DateDialog()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c: Calendar = Calendar.getInstance()
        val year: Int = c.get(Calendar.YEAR)
        val month: Int = c.get(Calendar.MONTH)
        val day: Int = c.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(
            requireActivity(),
            targetFragment as DatePickerDialog.OnDateSetListener?,
            year,
            month,
            day
        )
    }

}