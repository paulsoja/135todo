package com.paulsoia.todo135.business.model.task

import com.paulsoia.todo135.business.model.base.TypeEnum

enum class SortType(override val type: String) : TypeEnum {
    NAME("name"),
    TAG("tag"),
    DATE("date"),
    RESET("reset")
}