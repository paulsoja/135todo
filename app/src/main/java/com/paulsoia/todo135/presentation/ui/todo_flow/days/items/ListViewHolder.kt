package com.paulsoia.todo135.presentation.ui.todo_flow.days.items

import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_task_day.view.*

class ListViewHolder(view: View) : BaseViewHolder<Task>(view) {

    companion object {
        var callbackCheckbox: ((task: Task) -> Unit)? = null
        var callbackDrag: ((viewHolder: RecyclerView.ViewHolder) -> Unit)? = null
        var callbackItem: ((task: Task) -> Unit)? = null
        var callbackEmptyClick: ((position: Int) -> Unit)? = null
    }

    override fun bind(item: Task) {
        with(itemView) {
            if (item.isComplete) {
                checkbox.isChecked = true
                tvTask.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                checkbox.isChecked = false
                tvTask.paintFlags = 0
            }
            tvTask.text = item.message
            item.id?.let {
                checkbox.isEnabled = true
                setOnClickListener { callbackItem?.invoke(item) }
                checkbox.setOnClickListener {
                    callbackCheckbox?.invoke(item.also {
                        it.isComplete = checkbox.isChecked
                        it.message = tvTask.text.toString()
                        tvTask.paintFlags = if (it.isComplete) Paint.STRIKE_THRU_TEXT_FLAG else 0
                    })
                }
            } ?: run {
                checkbox.isEnabled = false
                setOnClickListener { callbackEmptyClick?.invoke(adapterPosition) }
            }

            /*var oldText = ""
            tvTask.doOnTextChanged { text, start, before, count ->
                val newText = text.toString().trim()
                oldText = newText
                GlobalScope.launch {
                    delay(1000)  //debounce timeOut
                    if (newText != oldText && item.id != null) return@launch
                    callback?.invoke(item.also { it.message = newText })
                }
            }*/

            ivDrag.setOnTouchListener { v, event ->
                if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                    callbackDrag?.invoke(ListViewHolder(itemView))
                }
                return@setOnTouchListener true
            }
        }
    }

}