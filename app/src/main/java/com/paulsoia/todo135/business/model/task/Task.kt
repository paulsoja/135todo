package com.paulsoia.todo135.business.model.task

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    val id: Long?,
    val date: String,
    var message: String,
    val tag: String,
    val category: String,
    val level: String,
    var isComplete: Boolean = false
) : Parcelable