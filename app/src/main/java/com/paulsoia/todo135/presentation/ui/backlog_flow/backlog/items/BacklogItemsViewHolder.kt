package com.paulsoia.todo135.presentation.ui.backlog_flow.backlog.items

import android.graphics.Paint
import android.view.View
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.base.BaseViewHolder
import com.paulsoia.todo135.presentation.utils.onClick
import kotlinx.android.synthetic.main.item_task.view.*

class BacklogItemsViewHolder(view: View) : BaseViewHolder<Task>(view) {

    companion object {
        var callbackTask: ((task: Task, position: Int) -> Unit)? = null
        var callbackMenu: ((task: Task, position: Int, v: View) -> Unit)? = null
        var callbackCheckbox: ((task: Task, position: Int) -> Unit)? = null
    }

    override fun bind(item: Task) {
        with(itemView) {
            if (item.isComplete) {
                checkbox.isChecked = true
                tvTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                checkbox.isChecked = false
                tvTitle.paintFlags = 0
            }
            tvTitle.text = item.message
            tvTag.text = item.tag
            tvDate.text = item.date
            onClick {
                callbackTask?.invoke(item, adapterPosition)
            }
            ivMore.onClick {
                callbackMenu?.invoke(item, adapterPosition, it)
            }
            checkbox.onClick {
                when(checkbox.isChecked) {
                    true -> {
                        item.isComplete = true
                        tvTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                    false -> {
                        item.isComplete = false
                        tvTitle.paintFlags = 0
                    }
                }
                callbackCheckbox?.invoke(item, adapterPosition)
            }
        }
    }
}