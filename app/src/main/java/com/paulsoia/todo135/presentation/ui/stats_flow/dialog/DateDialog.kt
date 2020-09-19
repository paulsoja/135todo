package com.paulsoia.todo135.presentation.ui.stats_flow.dialog

import android.os.Bundle
import android.view.View
import com.paulsoia.todo135.R
import com.paulsoia.todo135.presentation.base.BaseBottomSheetDialogFragment
import ru.slybeaver.slycalendarview.SlyCalendarDialog
import java.util.*


class DateDialog : BaseBottomSheetDialogFragment(), SlyCalendarDialog.Callback {

    companion object {
        fun newInstance() = DateDialog()
    }

    override val resLayout = R.layout.dialog_date

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*SlyCalendarDialog()
            .setSingle(false)
            .setCallback(this)
            .show(childFragmentManager, "TAG_SLYCALENDAR")*/
    }

    override fun onCancelled() {

    }

    override fun onDataSelected(
        firstDate: Calendar?,
        secondDate: Calendar?,
        hours: Int,
        minutes: Int
    ) {

    }

    /*override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
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
    }*/

}