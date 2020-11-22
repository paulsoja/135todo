package com.paulsoia.todo135.presentation.ui.backlog_flow.dialog

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.ScreenType
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.base.BaseBottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_edit_task.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditTaskDialog : BaseBottomSheetDialogFragment() {

    private val viewModel: EditTaskViewModel by viewModel()

    companion object {
        private const val TASK_ARG = "task"
        private const val SCREEN = "screen"

        fun newInstance(task: Task, screen: ScreenType) = EditTaskDialog().apply {
            arguments = bundleOf(TASK_ARG to task, SCREEN to screen)
        }
    }

    override val resLayout = R.layout.dialog_edit_task

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etMessage.requestFocus()
        arguments?.let {
            viewModel.message.value = it.takeIf { it.containsKey(TASK_ARG) }?.getParcelable(TASK_ARG)
                ?: throw IllegalArgumentException("`${Task::class.java.simpleName}` required")
            viewModel.screenType = it.takeIf { it.containsKey(SCREEN) }?.getParcelable(SCREEN)
                ?: throw IllegalArgumentException("`${Task::class.java.simpleName}` required")
        }
        initViews()
        deleteTaskResult()
    }

    private fun initViews() {
        viewModel.message.observe(viewLifecycleOwner, {
            etMessage.setText(it.message)
            etMessage.setSelection(etMessage.length())
        })
        btnEdit.setOnClickListener {
            val message = etMessage.text.toString()
            val task = viewModel.message.value
            task?.message = message
            task?.let { tsk ->
                viewModel.tryUpdateTask(tsk).observe(viewLifecycleOwner, {
                    if (it) { getUpdateCallback()?.onUpdateTask(tsk).also { dismiss() } }
                })
            }
        }
        btnDelete.setOnClickListener {
            val task = viewModel.message.value
            task?.let {
                viewModel.deleteTask(it)
            }
        }
    }

    private fun deleteTaskResult() {
        viewModel.deleteTaskResult.observe(viewLifecycleOwner, {
            if (it) { getUpdateCallback()?.onUpdateTask().also { dismiss() } }
        })
    }

    private fun getUpdateCallback(): UpdateBacklogCallback? = targetFragment as? UpdateBacklogCallback

}