package com.paulsoia.todo135.presentation.ui.todo_flow.days.dialogs.items

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.model.task.TaskMarker
import com.paulsoia.todo135.presentation.base.BaseViewHolder
import com.paulsoia.todo135.presentation.utils.inflate

class ImportAdapter : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var items: MutableList<TaskMarker> = mutableListOf()

    fun swapData(list: List<TaskMarker>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return ImportTaskViewHolder(parent.inflate(R.layout.item_task))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = items[position]
        (holder as? ImportTaskViewHolder)?.bind(element as Task)
    }

    override fun getItemCount() = items.size

}