package com.paulsoia.todo135.presentation.ui.backlog_flow.dialog

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.base.BaseBottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_edit_task.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditTaskDialog : BaseBottomSheetDialogFragment() {

    private val viewModel: EditTaskViewModel by viewModel()

    companion object {
        private const val TASK_ARG = "task"

        fun newInstance(task: Task) = EditTaskDialog().apply {
            arguments = bundleOf(TASK_ARG to task)
        }
    }

    override val resLayout = R.layout.dialog_edit_task

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etMessage.requestFocus()
        arguments?.let {
            viewModel.message.value = it.takeIf { it.containsKey(TASK_ARG) }
                ?.getParcelable(TASK_ARG)
                ?: throw IllegalArgumentException("`${Task::class.java.simpleName}` required")
        }
        initViews()
    }

    private fun initViews() {
        viewModel.message.observe(viewLifecycleOwner, Observer {
            etMessage.setText(it.message)
        })
        btnEdit.setOnClickListener {
            val message = etMessage.text.toString()
            val task = viewModel.message.value
            task?.message = message
            task?.let { tsk ->
                viewModel.tryUpdateTask(tsk).observe(viewLifecycleOwner, Observer {
                    if (it) { getUpdateCallback()?.onUpdateTask(tsk) }
                })
            }
        }
    }

    private fun getUpdateCallback(): UpdateBacklogCallback? = targetFragment as? UpdateBacklogCallback

}