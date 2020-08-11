package com.paulsoia.todo135.business.model.stats

import com.paulsoia.todo135.business.model.base.TypeEnum

enum class DateRangeType(override val type: String) : TypeEnum {
    DAY("day"),
    WEEK("week"),
    MONTH("month")
}