package com.paulsoia.todo135.presentation.ui.todo_flow.todo.items

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.paulsoia.todo135.presentation.base.BaseFragment
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.ui.todo_flow.todo.dialog.TaskBottomDialogFragment
import com.paulsoia.todo135.presentation.utils.onClick
import kotlinx.android.synthetic.main.item_days.*
import org.koin.android.ext.android.inject

class TodoItemFragment : BaseFragment(), TaskBottomDialogFragment.UpdateTaskScreenCallback {

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
            TaskBottomDialogFragment.newInstance().apply {
                setTargetFragment(this@TodoItemFragment, 0)
            }.show(parentFragmentManager, "tag")
        }
        btnOne.onClick {
            etOne.isEnabled = true
            btnOne.setImageResource(R.drawable.ic_check)
        }
        getTaskList()
    }

    override fun onUpdateTask(date: String) {
        todoItemViewModel.getTaskByDate(date)
    }

    private fun getTaskList() {
        todoItemViewModel.result.observe(viewLifecycleOwner, Observer {

        })
    }

}