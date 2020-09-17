package com.paulsoia.todo135.presentation.ui.stats_flow.stats

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.DatePicker
import android.widget.PopupMenu
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.stats.DateRangeType
import com.paulsoia.todo135.presentation.base.BaseFragment
import com.paulsoia.todo135.presentation.ui.stats_flow.dialog.DateDialog
import kotlinx.android.synthetic.main.fragment_stats.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import java.text.DateFormat

class StatsFragment : BaseFragment(), DatePickerDialog.OnDateSetListener {

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
    }

    private fun initListeners() {
        holderStats.setOnClickListener {
            DateDialog.newInstance().apply {
                setTargetFragment(this@StatsFragment, 0)
            }.show(parentFragmentManager, "date_picker")
        }
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

}