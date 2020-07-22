package com.paulsoia.todo135.presentation.ui.todo_flow.days.items

import android.view.View
import com.paulsoia.todo135.business.model.task.Title
import com.paulsoia.todo135.presentation.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_task_title.view.*

class TitleViewHolder(view: View) : BaseViewHolder<Title>(view) {
    override fun bind(item: Title) {
        itemView.tvTitle.text = item.title
    }
}