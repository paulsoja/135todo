package com.paulsoia.todo135.business.model.tag

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Tag(
    val id: Long?,
    val tag: String,
    var count: Int,
    var isChecked: Boolean = false
) : Parcelable, TagMarker {

    companion object {
        fun empty() = Tag(
            id = null,
            tag = "",
            count = 0
        )
    }

}