package com.paulsoia.todo135.presentation.ui.todo_flow.days.import_task

import android.view.View
import androidx.core.view.isVisible
import com.paulsoia.todo135.business.model.task.LevelType
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_task.view.*
import java.text.SimpleDateFormat
import java.util.*

class ImportTaskViewHolder(view: View) : BaseViewHolder<Task>(view) {

    companion object {
        var callbackItem: ((task: Task) -> Unit)? = null
    }

    override fun bind(item: Task) {
        with(itemView) {
            setOnClickListener {
                callbackItem?.invoke(item)
            }
            tvTitle.text = item.message
            checkbox.isVisible = false
            ivPush.isVisible = false
        }
    }

}