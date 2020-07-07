package com.paulsoia.todo135.presentation.ui.todo_flow.todo.items

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.paulsoia.todo135.presentation.base.BaseFragment
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.NewTaskDialog
import com.paulsoia.todo135.presentation.utils.onClick
import kotlinx.android.synthetic.main.item_days.*
import org.koin.android.ext.android.inject

class TodoItemFragment : BaseFragment(), NewTaskDialog.UpdateTaskScreenCallback {

    companion object {
        private val ARGS = "args"

        fun newInstance(count: Int) = TodoItemFragment()
            .apply {
                arguments = bundleOf(ARGS to count)
            }
    }

    private val todoItemViewModel: TodoItemViewModel by inject()

    var callback: ((task: Task) -> Unit)? = null

    private var count: Int = 111

    override val layoutRes: Int = R.layout.item_days

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        count = arguments?.getInt(ARGS) as Int
        fabAdd.onClick {
            NewTaskDialog.newInstance().apply {
                setTargetFragment(this@TodoItemFragment, 0)
            }.show(parentFragmentManager, "tag")
        }
        btnOne.onClick {
            etOne.isEnabled = true
            btnOne.setImageResource(R.drawable.ic_check)
        }
        getTaskList()
        check()
    }

    override fun onUpdateTask(date: String) {
        todoItemViewModel.getTaskByDate(date)
    }

    private fun getTaskList() {
        todoItemViewModel.result.observe(viewLifecycleOwner, Observer {

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