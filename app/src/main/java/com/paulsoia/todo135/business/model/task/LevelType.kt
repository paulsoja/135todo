package com.paulsoia.todo135.business.model.task

import com.paulsoia.todo135.business.model.base.TypeEnum

enum class LevelType(override val type: String) : TypeEnum {
    BIG("big"),
    MEDIUM("medium"),
    SMALL("small"),
    NONE("")
}