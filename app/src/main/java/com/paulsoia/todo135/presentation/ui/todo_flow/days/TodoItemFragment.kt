package com.paulsoia.todo135.presentation.ui.todo_flow.days

import android.graphics.Paint
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.paulsoia.todo135.presentation.base.BaseFragment
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.utils.onClick
import kotlinx.android.synthetic.main.item_days.*
import org.koin.android.ext.android.inject

class TodoItemFragment : BaseFragment() {

    companion object {
        private val ARGS = "args"
        private const val ITEMS = "items"

        fun newInstance(count: Int, items: ArrayList<Task>) = TodoItemFragment()
            .apply {
                arguments = bundleOf(ARGS to count, ITEMS to items)
            }
    }

    private val mapTaskEtBig = mutableMapOf<EditText, Task>()
    private val mapTaskEtMedium = mutableMapOf<EditText, Task>()
    private val mapTaskEtSmall = mutableMapOf<EditText, Task>()

    private val todoItemViewModel: TodoItemViewModel by inject()

    private var count: Int = 111

    override val layoutRes: Int = R.layout.item_days

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        count = arguments?.getInt(ARGS) as Int
        todoItemViewModel.result.value = arguments?.getParcelableArrayList<Task>(ITEMS) as ArrayList<Task>
        btnOne.onClick {
            etOne.isEnabled = true
            btnOne.setImageResource(R.drawable.ic_check)
        }
        getTaskList()
        check()
    }

    private fun updateStatusTask() {

    }

    private fun checkCompletableStatus(checkBox: CheckBox, editText: EditText, task: Task) {
        if (task.isComplete) {
            editText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            checkBox.isChecked = true
        } else {
            editText.paintFlags = 0
            checkBox.isChecked = false
        }
    }

    private fun getTaskList() {
        todoItemViewModel.result.observe(viewLifecycleOwner, Observer {
            it.find { it.level == "big" }?.let {
                etOne.setText(it.message)
                mapTaskEtBig.clear()
                mapTaskEtBig.put(etOne, it)
            }
            it.filter { it.level == "medium" }.apply {
                val etList = mutableListOf(etTwo, etThree, etFour)
                val result = etList.take(size)
                mapTaskEtMedium.clear()
                result.forEachIndexed { index, editText ->
                    editText.setText(this[index].message)
                    mapTaskEtMedium[editText] = this[index]
                }
            }
            it.filter { it.level == "small" }.apply {
                val etList = mutableListOf(etFive, etSix, etSeven, etEight, etNine)
                val result = etList.take(size)
                mapTaskEtSmall.clear()
                result.forEachIndexed { index, editText ->
                    editText.setText(this[index].message)
                    mapTaskEtSmall[editText] = this[index]
                }
            }
        })
    }

    private fun toggleEditText(editText: EditText, imageView: ImageView) {
        imageView.onClick {
            if (editText.isEnabled) {
                editText.isEnabled = false
                imageView.setImageResource(R.drawable.ic_edit)
            } else {
                editText.isEnabled = true
                imageView.setImageResource(R.drawable.ic_check)
            }
        }
    }

    private fun check() {
        toggleEditText(etOne, btnOne)
        toggleEditText(etTwo, btnTwo)
        toggleEditText(etThree, btnThree)
        toggleEditText(etFour, btnFour)
        toggleEditText(etFive, btnFive)
        toggleEditText(etSix, btnSix)
        toggleEditText(etSeven, btnSeven)
        toggleEditText(etEight, btnEight)
        toggleEditText(etNine, btnNine)
    }

}