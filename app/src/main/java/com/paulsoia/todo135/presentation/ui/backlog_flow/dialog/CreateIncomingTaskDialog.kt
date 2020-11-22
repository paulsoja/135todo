package com.paulsoia.todo135.presentation.ui.backlog_flow.dialog

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.LevelType
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.base.BaseBottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_new_task.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class CreateIncomingTaskDialog : BaseBottomSheetDialogFragment() {

    private val viewModel: CreateIncomingTaskViewModel by viewModel()

    companion object {
        private const val TASK_ARG = "task_arg"
        private const val TASK_POS = "task_pos"

        fun newInstance(task: Task? = null, position: Int = -1) = CreateIncomingTaskDialog().apply {
            arguments = bundleOf(TASK_ARG to task, TASK_POS to position)
        }
    }

    override val resLayout = R.layout.dialog_new_task

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            it.takeIf { it.containsKey(TASK_ARG) }?.getParcelable<Task>(TASK_ARG)?.let { tvTask.setText(it.message) }
            it.takeIf { it.containsKey(TASK_POS) }?.getInt(TASK_POS)
        }
        tvTask.requestFocus()
        initLoader()
        warning()
        updateBacklog()
        tvCreate.setOnClickListener { saveTask() }
        tvImport.setOnClickListener { openImportDialog() }
    }

    private fun openImportDialog() {
        dismiss()
    }

    private fun saveTask() {
        viewModel.trySaveTask(getTaskModel())
    }

    private fun warning() {
        viewModel.warningResult.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })
    }

    private fun getTaskModel(): Task {
        val task: Task
        val message = tvTask.text.toString()
        val level = LevelType.NONE
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = sdf.format(System.currentTimeMillis())
        val category = ""
        val tag = ""
        task = Task(null, 0, message, tag, category, level)
        return task
    }

    private fun updateBacklog() {
        viewModel.saveTaskResult.observe(viewLifecycleOwner, {
            getUpdateCallback()?.onUpdateTask().also { dismiss() }
        })
    }

    private fun initLoader() {
        viewModel.isViewLoading.observe(viewLifecycleOwner, {
            loader.isVisible = it
        })
    }

    private fun getUpdateCallback(): UpdateBacklogCallback? = targetFragment as? UpdateBacklogCallback

}