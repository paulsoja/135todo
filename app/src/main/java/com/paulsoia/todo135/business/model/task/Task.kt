package com.paulsoia.todo135.business.model.task

data class Task(
    val id: Long?,
    val date: String,
    val message: String,
    val tag: String,
    val category: String,
    val level: String,
    var isComplete: Boolean = false
)