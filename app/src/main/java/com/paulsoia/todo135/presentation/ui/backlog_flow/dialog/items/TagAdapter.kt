package com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.items

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.tag.Tag
import com.paulsoia.todo135.business.model.tag.TagMarker
import com.paulsoia.todo135.presentation.base.BaseViewHolder
import com.paulsoia.todo135.presentation.utils.inflate

class TagAdapter : RecyclerView.Adapter<BaseViewHolder<*>>() {

    companion object {
        private const val TYPE_LIST = 0
    }

    private val tagViewHolder = TagViewHolder

    internal var callback: TagListener? = null
        set(value) {
            field = value
            tagViewHolder.callbackTag = { tag, pos -> callback?.onTagClicked(tag, pos) }
        }

    private val items = mutableListOf<TagMarker>()

    internal fun swapData(list: List<TagMarker>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    internal fun updateItem(tag: Tag, position: Int) {
        items.find { (it as? Tag)?.id == tag.id }.apply {
            (this as? Tag)?.isChecked = tag.isChecked
            notifyItemChanged(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when(viewType) {
            TYPE_LIST -> TagViewHolder(parent.inflate(R.layout.item_tag))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Tag -> TYPE_LIST
            else -> throw IllegalArgumentException("Invalid type of item $position")
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = items[position]
        when (holder) {
            is TagViewHolder -> holder.bind(element as Tag)
            else -> throw IllegalArgumentException()
        }
    }

    interface TagListener {
        fun onTagClicked(tag: Tag, position: Int)
    }

}