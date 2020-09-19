package com.paulsoia.todo135.presentation.ui.stats_flow.stats

import android.app.DatePickerDialog
import android.content.res.Resources
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.DatePicker
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.stats.DateRangeType
import com.paulsoia.todo135.presentation.base.BaseFragment
import com.paulsoia.todo135.presentation.ui.stats_flow.dialog.DateDialog
import kotlinx.android.synthetic.main.fragment_stats.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.slybeaver.slycalendarview.SlyCalendarDialog
import java.util.*
import java.text.DateFormat

class StatsFragment : BaseFragment(), DatePickerDialog.OnDateSetListener, SlyCalendarDialog.Callback {

    companion object {
        fun newInstance() = StatsFragment()
    }

    override val layoutRes = R.layout.fragment_stats

    private val viewModel: StatsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        holderDateRange.setOnClickListener { openDateMenu() }
        updateDateRangeText()
        initListeners()
        initDate()
        isViewLoader()
    }

    private fun initCalendar() {
        SlyCalendarDialog().apply {
            setStyle(DialogFragment.STYLE_NORMAL, R.style.AppBottomSheetDialogThemeNotFloating)
        }
            .setSingle(false)
            .setFirstMonday(false)
            .setBackgroundColor(requireContext().resources.getColor(R.color.white, requireActivity().theme))
            .setHeaderColor(requireContext().resources.getColor(R.color.main_blue_light, requireActivity().theme))
            .setCallback(this@StatsFragment)
            .show(childFragmentManager, "TAG_SLYCALENDAR")
    }

    private fun initListeners() {
        holderStats.setOnClickListener { initCalendar() }
    }

    private fun setStats() {

    }

    private fun openDateMenu() {
        val popup = PopupMenu(requireContext(), holderDateRange)
        popup.inflate(R.menu.date_range_menu)
        popup.gravity = Gravity.CENTER
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menuDays -> setStatRange(DateRangeType.DAY)
                R.id.menuWeeks -> setStatRange(DateRangeType.WEEK)
                R.id.menuMonths -> setStatRange(DateRangeType.MONTH)
            }
            return@setOnMenuItemClickListener true
        }
        popup.show()
    }

    private fun updateDateRangeText(value: DateRangeType? = null) {
        tvDateRange.text = when(value ?: viewModel.getDateRangeType()) {
            DateRangeType.DAY -> getString(R.string.stat_day_text)
            DateRangeType.WEEK -> getString(R.string.stat_week_text)
            DateRangeType.MONTH -> getString(R.string.stat_month_text)
            DateRangeType.CUSTOM -> getString(R.string.stat_custom_text)
        }
    }

    private fun setStatRange(value: DateRangeType) {
        updateDateRangeText(value)
        viewModel.saveDateRangeType(value)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val c: Calendar = Calendar.getInstance()
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val currentDateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.time)
        tvDate.text = currentDateString
        updateDateRangeText(DateRangeType.CUSTOM)
    }

    private fun initDate() {
        val c: Calendar = Calendar.getInstance()
        val currentDateString = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(c.time)
        tvDate.text = currentDateString
        updateDateRangeText(DateRangeType.CUSTOM)
    }

    override fun onCancelled() {

    }

    override fun onDataSelected(
        firstDate: Calendar?,
        secondDate: Calendar?,
        hours: Int,
        minutes: Int
    ) {
        var first = ""
        var second = ""
        firstDate?.let { first = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(it.time) }
        secondDate?.let { second = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(it.time) }
        val date = "$first - $second"
        tvDate.text = date
        updateDateRangeText(DateRangeType.CUSTOM)
        viewModel.getStatsByDate(firstDate?.time, secondDate?.time)
    }

    private fun isViewLoader() {
        viewModel.isViewLoading.observe(viewLifecycleOwner, {
            progressBar.isVisible = it
        })
    }

}