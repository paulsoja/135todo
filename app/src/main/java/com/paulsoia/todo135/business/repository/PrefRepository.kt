package com.paulsoia.todo135.business.repository

import com.paulsoia.todo135.business.model.task.FilterType
import com.paulsoia.todo135.business.model.task.SortType

interface PrefRepository {

    fun setHowToFilterTask(type: FilterType)
    fun getHowToFilterTask(): FilterType?

    fun setHowToSortTask(type: SortType)
    fun getHowToSortTask(): SortType?

    fun clear()

}