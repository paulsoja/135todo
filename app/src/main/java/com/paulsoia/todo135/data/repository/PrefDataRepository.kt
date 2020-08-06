package com.paulsoia.todo135.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.paulsoia.todo135.business.repository.PrefRepository

class PrefDataRepository(
    private val pref: SharedPreferences
) : PrefRepository {

    companion object {
        const val FILTER_TYPE = "filter_type"
        const val SORT_TYPE = "sort_type"
    }

    override fun setHowToFilterTask(type: String) = pref.edit { putString(FILTER_TYPE, type) }

    override fun getHowToFilterTask(): String? = pref.getString(FILTER_TYPE, "all")

    override fun setHowToSortTask(type: String) = pref.edit { putString(SORT_TYPE, type) }

    override fun getHowToSortTask(): String? = pref.getString(SORT_TYPE, "reset")

    override fun clear() = pref.edit().clear().apply()

}