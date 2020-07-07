package com.paulsoia.todo135.presentation.ui.backlog_flow.backlog.list

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.utils.onClick
import kotlinx.android.synthetic.main.item_task.view.*

class BacklogTaskAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal var callback: Callback? = null

    private val items = mutableListOf<Task>()

    internal fun swapData(list: List<Task>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ViewHolder)?.bind(items[position], position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(model: Task, position: Int) {
            with(itemView) {
                checkbox.isChecked = model.isComplete
                tvTitle.text = model.message
                tvTag.text = model.tag
                tvDate.text = model.date
                onClick {
                    callback?.onTaskClicked(model, position)
                }
                checkbox.onClick {
                    when(checkbox.isChecked) {
                        true -> {
                            model.isComplete = true
                            tvTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                        }
                        false -> {
                            model.isComplete = false
                            tvTitle.paintFlags = 0
                        }
                    }
                    callback?.onCheckboxClicked(model, position)
                }
            }
        }
    }

    interface Callback {
        fun onCheckboxClicked(task: Task, position: Int)
        fun onTaskClicked(task: Task, position: Int)
    }

}


