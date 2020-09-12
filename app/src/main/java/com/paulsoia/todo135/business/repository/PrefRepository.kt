package com.paulsoia.todo135.business.repository

import com.paulsoia.todo135.business.model.stats.DateRangeType
import com.paulsoia.todo135.business.model.task.FilterType
import com.paulsoia.todo135.business.model.task.SortType

interface PrefRepository {

    fun setHowToFilterTask(type: FilterType)
    fun getHowToFilterTask(): FilterType?

    fun setHowToSortTask(type: SortType)
    fun getHowToSortTask(): SortType?

    fun setStatsRange(type: DateRangeType)
    fun getStatsRange(): DateRangeType?

    fun saveName(name: String)
    fun getName(): String?

    fun clear()

}