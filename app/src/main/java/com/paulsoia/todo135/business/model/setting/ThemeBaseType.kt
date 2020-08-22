package com.paulsoia.todo135.business.model.setting

import com.paulsoia.todo135.business.model.base.TypeEnum

enum class ThemeBaseType(override val type: String) : TypeEnum {
    BLUE("blue"),
    INDIGO("indigo"),
    GREEN("green"),
    RED("red"),
}