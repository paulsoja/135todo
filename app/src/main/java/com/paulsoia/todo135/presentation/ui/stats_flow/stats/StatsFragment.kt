package com.paulsoia.todo135.presentation.ui.stats_flow.stats

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.stats.DateRangeType
import com.paulsoia.todo135.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_stats.*

class StatsFragment : BaseFragment() {

    companion object {
        fun newInstance() = StatsFragment()
    }

    override val layoutRes = R.layout.fragment_stats

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDateRange.setOnClickListener { openDateMenu() }
    }

    private fun openDateMenu() {
        val popup = PopupMenu(requireContext(), tvDateRange)
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

    private fun setStatRange(value: DateRangeType) {
        when(value) {
            DateRangeType.DAY -> {}
            DateRangeType.WEEK -> {}
            DateRangeType.MONTH -> {}
        }
    }

}