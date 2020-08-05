package com.paulsoia.todo135.presentation.ui.backlog_flow.backlog.items

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.model.task.TaskMarker
import com.paulsoia.todo135.business.model.task.Title
import com.paulsoia.todo135.presentation.base.BaseViewHolder
import com.paulsoia.todo135.presentation.ui.todo_flow.days.items.TitleViewHolder
import com.paulsoia.todo135.presentation.utils.inflate

class BacklogTaskAdapter : RecyclerView.Adapter<BaseViewHolder<*>>() {

    companion object {
        private const val TYPE_TITLE = 0
        private const val TYPE_LIST = 1
    }

    private val backlogItemsViewHolder = BacklogItemsViewHolder

    internal var callback: TaskListener? = null
        set(value) {
            field = value
            backlogItemsViewHolder.callbackTask = { task, pos -> callback?.onTaskClicked(task, pos) }
            backlogItemsViewHolder.callbackMenu = { task, pos, v -> callback?.onMenuClicked(task, pos, v) }
            backlogItemsViewHolder.callbackCheckbox = { task, pos -> callback?.onCheckboxClicked(task, pos) }
            backlogItemsViewHolder.callbackTag = { task, pos -> callback?.onTagClicked(task, pos) }
        }

    private val items = mutableListOf<TaskMarker>()

    internal fun swapData(list: List<TaskMarker>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when(viewType) {
            TYPE_TITLE -> TitleViewHolder(parent.inflate(R.layout.item_task_title))
            TYPE_LIST -> BacklogItemsViewHolder(parent.inflate(R.layout.item_task))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Title -> TYPE_TITLE
            is Task -> TYPE_LIST
            else -> throw IllegalArgumentException("Invalid type of item $position")
        }
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = items[position]
        when (holder) {
            is TitleViewHolder -> holder.bind(element as Title)
            is BacklogItemsViewHolder -> holder.bind(element as Task)
            else -> throw IllegalArgumentException()
        }
    }

    interface TaskListener {
        fun onCheckboxClicked(task: Task, position: Int)
        fun onTaskClicked(task: Task, position: Int)
        fun onMenuClicked(task: Task, position: Int, v: View)
        fun onTagClicked(task: Task, position: Int)
    }

}


