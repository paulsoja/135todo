package com.paulsoia.todo135.presentation.ui.todo_flow.days.items

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.model.task.TaskMarker
import com.paulsoia.todo135.business.model.task.Title
import com.paulsoia.todo135.presentation.base.BaseViewHolder
import com.paulsoia.todo135.presentation.utils.inflate

class TodoDayAdapter : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var items: MutableList<TaskMarker> = mutableListOf()

    private val listViewHolder = ListViewHolder

    private val TYPE_TITLE = 0
    private val TYPE_LIST = 1

    var callback: TaskListener? = null
    set(value) {
        field = value
        listViewHolder.callback = { task -> callback?.onCheckboxClick(task) }
    }

    fun swapData(list: List<TaskMarker>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun updateTitleByPosition(position: Int, title: Title) {
        items.mapIndexed { index, taskMarker ->

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_TITLE -> TitleViewHolder(parent.inflate(R.layout.item_task_title))
            TYPE_LIST -> ListViewHolder(parent.inflate(R.layout.item_task_day))
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
            is ListViewHolder -> holder.bind(element as Task)
            else -> throw IllegalArgumentException()
        }
    }

    interface TaskListener {
        fun onCheckboxClick(task: Task)
    }

}