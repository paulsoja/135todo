package com.paulsoia.todo135.presentation.ui.todo_flow.days.items

import android.graphics.Paint
import android.view.View
import androidx.core.widget.doOnTextChanged
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.base.BaseViewHolder
import com.paulsoia.todo135.presentation.utils.onClick
import kotlinx.android.synthetic.main.item_task_day.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ListViewHolder(view: View) : BaseViewHolder<Task>(view) {

    companion object {
        var callback: ((task: Task) -> Unit)? = null
    }

    override fun bind(item: Task) {
        with(itemView) {
            if (item.isComplete) {
                checkbox.isChecked = true
                etTask.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                checkbox.isChecked = false
                etTask.paintFlags = 0
            }
            etTask.setText(item.message)
            checkbox.onClick {
                if (item.id != null && !etTask.text.isNullOrBlank()) {
                    callback?.invoke(item.also {
                        it.isComplete = checkbox.isChecked
                        it.message = etTask.text.toString()
                        etTask.paintFlags = if (it.isComplete) Paint.STRIKE_THRU_TEXT_FLAG else 0
                    })
                }
            }
            var oldText = ""
            etTask.doOnTextChanged { text, start, before, count ->
                val newText = text.toString().trim()
                //if (newText == oldText)
                    oldText = newText
                GlobalScope.launch {
                    delay(1000)  //debounce timeOut
                    if (newText != oldText && item.id != null)
                        return@launch
                    callback?.invoke(item.also {
                        //it.isComplete = checkbox.isChecked
                        it.message = newText
                        //etTask.paintFlags = if (it.isComplete) Paint.STRIKE_THRU_TEXT_FLAG else 0
                    })
                }
            }
        }
    }

}