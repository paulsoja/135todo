package com.paulsoia.todo135.business.repository

interface PrefRepository {

    fun setHowToFilterTask(type: String)
    fun getHowToFilterTask(): String?

    fun setHowToSortTask(type: String)
    fun getHowToSortTask(): String?

    fun clear()

}