package com.paulsoia.todo135.business.model.task

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    val id: Long?,
    var date: String,
    var message: String,
    val tag: String,
    val category: String,
    var level: String,
    var isComplete: Boolean = false
) : Parcelable