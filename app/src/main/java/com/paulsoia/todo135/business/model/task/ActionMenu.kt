package com.paulsoia.todo135.business.model.task

import com.paulsoia.todo135.business.model.base.TypeEnum

enum class ActionMenu(override val type: String) : TypeEnum {
    MOVE("move"),
    COPY("copy")
}