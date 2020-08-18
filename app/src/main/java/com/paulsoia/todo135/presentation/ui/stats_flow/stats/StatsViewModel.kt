package com.paulsoia.todo135.presentation.ui.stats_flow.stats

import androidx.lifecycle.ViewModel
import com.paulsoia.todo135.business.model.stats.DateRangeType
import com.paulsoia.todo135.business.repository.PrefRepository

class StatsViewModel(
    private val prefRepository: PrefRepository
) : ViewModel() {

    internal fun saveDateRangeType(value: DateRangeType) = prefRepository.setStatsRange(value)

    internal fun getDateRangeType(): DateRangeType = prefRepository.getStatsRange() ?: DateRangeType.DAY

}