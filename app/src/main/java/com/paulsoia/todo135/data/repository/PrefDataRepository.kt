package com.paulsoia.todo135.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.paulsoia.todo135.business.model.stats.DateRangeType
import com.paulsoia.todo135.business.model.task.FilterType
import com.paulsoia.todo135.business.model.task.SortType
import com.paulsoia.todo135.business.repository.PrefRepository
import com.paulsoia.todo135.presentation.utils.getEnumTypeValue

class PrefDataRepository(
    private val pref: SharedPreferences
) : PrefRepository {

    companion object {
        const val FILTER_TYPE = "filter_type"
        const val SORT_TYPE = "sort_type"
        const val STATS_TYPE = "stats_type"
        const val SETTINGS_NAME = "settings_name"
    }

    override fun setHowToFilterTask(type: FilterType) = pref.edit { putString(FILTER_TYPE, type.type) }
    override fun getHowToFilterTask(): FilterType? = getEnumTypeValue<FilterType>(pref.getString(FILTER_TYPE, FilterType.ALL.type) ?: FilterType.ALL.type)

    override fun setHowToSortTask(type: SortType) = pref.edit { putString(SORT_TYPE, type.type) }
    override fun getHowToSortTask(): SortType? = getEnumTypeValue<SortType>(pref.getString(SORT_TYPE, SortType.RESET.type) ?: SortType.RESET.type)

    override fun setStatsRange(type: DateRangeType) = pref.edit { putString(STATS_TYPE, type.type) }
    override fun getStatsRange(): DateRangeType? = getEnumTypeValue<DateRangeType>(pref.getString(STATS_TYPE, DateRangeType.DAY.type) ?: DateRangeType.DAY.type)

    override fun saveName(name: String) = pref.edit { putString(SETTINGS_NAME, name) }
    override fun getName(): String? = pref.getString(SETTINGS_NAME, "")

    override fun clear() = pref.edit().clear().apply()

}