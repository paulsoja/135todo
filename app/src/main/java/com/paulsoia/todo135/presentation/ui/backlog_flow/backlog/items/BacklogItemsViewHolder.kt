package com.paulsoia.todo135.presentation.ui.backlog_flow.backlog.items

import android.graphics.Paint
import android.view.View
import androidx.core.view.isVisible
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.base.BaseViewHolder
import com.paulsoia.todo135.presentation.utils.getDateTime
import kotlinx.android.synthetic.main.item_task.view.*

class BacklogItemsViewHolder(view: View) : BaseViewHolder<Task>(view) {

    companion object {
        var callbackTask: ((task: Task) -> Unit)? = null
        var callbackMenu: ((task: Task, position: Int, v: View) -> Unit)? = null
        var callbackCheckbox: ((task: Task, position: Int) -> Unit)? = null
        var callbackTag: ((task: Task, position: Int) -> Unit)? = null
    }

    override fun bind(item: Task) {
        with(itemView) {
            if (item.isComplete) {
                checkbox.isChecked = true
                tvTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                ivPush.isVisible = false
            } else {
                checkbox.isChecked = false
                tvTitle.paintFlags = 0
                ivPush.isVisible = true
            }
            tvTitle.text = item.message
            tvTag.text = if (item.tag.isNotBlank()) item.tag else context.getString(R.string.backlog_add_tag)
            tvDate.text = item.date.getDateTime()
            setOnClickListener { callbackTask?.invoke(item) }
            ivPush.setOnClickListener { callbackMenu?.invoke(item, adapterPosition, it) }
            tvTag.setOnClickListener { callbackTag?.invoke(item, adapterPosition) }
            checkbox.setOnClickListener {
                when(checkbox.isChecked) {
                    true -> {
                        item.isComplete = true
                        tvTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                        ivPush.isVisible = false
                    }
                    false -> {
                        item.isComplete = false
                        tvTitle.paintFlags = 0
                        ivPush.isVisible = true
                    }
                }
                callbackCheckbox?.invoke(item, adapterPosition)
            }
        }
    }
}