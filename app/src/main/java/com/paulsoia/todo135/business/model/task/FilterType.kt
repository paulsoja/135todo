package com.paulsoia.todo135.business.model.task

import com.paulsoia.todo135.business.model.base.TypeEnum

enum class FilterType(override val type: String) : TypeEnum {
    TAG("tag"),
    DATE("date"),
    ALL("all")
}