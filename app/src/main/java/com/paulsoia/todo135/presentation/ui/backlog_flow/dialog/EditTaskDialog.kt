package com.paulsoia.todo135.presentation.ui.backlog_flow.dialog

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.base.BaseBottomSheetDialogFragment
import com.paulsoia.todo135.presentation.utils.onClick
import kotlinx.android.synthetic.main.dialog_edit_task.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditTaskDialog : BaseBottomSheetDialogFragment() {

    private val editTaskViewModel: EditTaskViewModel by viewModel()

    companion object {
        private const val TASK_ARG = "task"
        private const val POSITION_ARG = "position"

        fun newInstance(task: Task, position: Int) = EditTaskDialog().apply {
            arguments = bundleOf(TASK_ARG to task, POSITION_ARG to position)
        }
    }

    override val resLayout = R.layout.dialog_edit_task

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etMessage.requestFocus()
        arguments?.let {
            editTaskViewModel.message.value = it.takeIf { it.containsKey(TASK_ARG) }
                ?.getParcelable(TASK_ARG)
                ?: throw IllegalArgumentException("`${Task::class.java.simpleName}` required")
        }
        initViews()
    }

    private fun initViews() {
        editTaskViewModel.message.observe(viewLifecycleOwner, Observer {
            etMessage.setText(it.message)
        })
        btnEdit.onClick {
            val message = etMessage.text.toString()
            val task = editTaskViewModel.message.value
            task?.message = message
            task?.let {
                editTaskViewModel.tryUpdateTask(it)
            }

        }
    }
}