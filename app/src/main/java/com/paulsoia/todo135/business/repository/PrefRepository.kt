package com.paulsoia.todo135.business.repository

interface PrefRepository {

    fun setHowToFilterTask(type: String)
    fun getHowToFilterTask(): String?

    fun clear()

}