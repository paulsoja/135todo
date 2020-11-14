package com.paulsoia.todo135.business.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class ScreenType : Parcelable {
    TODO_SCREEN,
    BACKLOG_SCREEN
}