package com.paulsoia.todo135.presentation.ui.backlog_flow.dialog

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.base.BaseBottomSheetDialogFragment

class MenuDialog : BaseBottomSheetDialogFragment() {

    companion object {
        private const val TASK_ARG = "task"

        fun newInstance(task: Task) = MenuDialog().apply {
            arguments = bundleOf(TASK_ARG to task)
        }
    }

    override val resLayout = R.layout.dialog_menu

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}