package com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.items

import android.view.View
import com.paulsoia.todo135.business.model.tag.Tag
import com.paulsoia.todo135.presentation.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_tag.view.*

class TagViewHolder(view: View) : BaseViewHolder<Tag>(view) {

    companion object {
        var callbackTag: ((tag: Tag, position: Int) -> Unit)? = null
    }

    override fun bind(item: Tag) {
        with(itemView) {
            tvTag.text = item.tag
            setOnClickListener { callbackTag?.invoke(item, adapterPosition) }
            radioBtn.isChecked = item.isChecked
        }
    }

}