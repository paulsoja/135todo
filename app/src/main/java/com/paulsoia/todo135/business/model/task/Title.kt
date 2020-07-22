package com.paulsoia.todo135.business.model.task

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Title(
    var title: String
) : Parcelable, TaskMarker