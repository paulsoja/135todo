package com.paulsoia.todo135.presentation.ui.backlog_flow.backlog.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.utils.onClick
import kotlinx.android.synthetic.main.item_task.view.*

class BacklogTaskAdapter(private val items: List<Task>) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(model: Task, position: Int) {
        with(itemView) {
            checkbox.isChecked = model.isComplete
            tvTitle.text = model.message
            tvTag.text = model.tag
            tvDate.text = model.date
            onClick {
                Toast.makeText(context, "click on the task $position", Toast.LENGTH_SHORT).show() }
        }
    }
}
