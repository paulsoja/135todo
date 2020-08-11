package com.paulsoia.todo135.business.model.task

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    val id: Long?,
    var date: String,
    var message: String,
    var tag: String,
    val category: String,
    var level: LevelType,
    var isComplete: Boolean = false
) : Parcelable, TaskMarker {

    companion object {
        fun empty(
            id: Long? = null,
            date: String = "",
            message: String = "",
            tag: String = "",
            category: String = "",
            level: LevelType = LevelType.NONE,
            isComplete: Boolean = false
        ) = Task(
            id = id,
            date = date,
            message = message,
            tag = tag,
            category = category,
            level = level,
            isComplete = isComplete
        )
    }

}