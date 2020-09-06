package com.paulsoia.todo135.presentation.ui.todo_flow.days.items

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.model.task.TaskMarker
import com.paulsoia.todo135.business.model.task.Title
import com.paulsoia.todo135.presentation.base.BaseViewHolder
import com.paulsoia.todo135.presentation.utils.inflate
import java.util.*

class TodoDayAdapter : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var items: MutableList<TaskMarker> = mutableListOf()

    private val listViewHolder = ListViewHolder

    private val TYPE_TITLE = 0
    private val TYPE_LIST = 1

    var callback: TaskListener? = null
    set(value) {
        field = value
        listViewHolder.callbackCheckbox = { task -> callback?.onCheckboxClick(task) }
        listViewHolder.callbackDrag = { vh -> callback?.onDragItem(vh) }
        listViewHolder.callbackItem = { task -> callback?.onItemClick(task) }
    }

    fun swapData(list: List<TaskMarker>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    fun updateItemById(task: TaskMarker) {
        if (task is Task) {
            items.forEachIndexed { index, taskMarker ->
                if ((taskMarker as? Task)?.id == task.id) {
                    (taskMarker as? Task)?.isComplete = task.isComplete
                    (taskMarker as? Task)?.message = task.message
                    notifyItemChanged(index)
                }
            }
        }
    }

    fun moveItem(from: Int, to: Int) {
        if (from > 0 && from < items.size && to > 0 && to < items.size) {
            Collections.swap(items, from, to)
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
        fun onDragItem(viewHolder: RecyclerView.ViewHolder)
        fun onItemClick(task: Task)
    }

}