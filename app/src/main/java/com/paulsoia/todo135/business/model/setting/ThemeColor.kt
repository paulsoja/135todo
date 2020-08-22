package com.paulsoia.todo135.business.model.setting

data class ThemeColor(
    val color: ThemeBaseType,
    val name: String,
    val isActive: Boolean = false
)