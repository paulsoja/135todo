package com.paulsoia.todo135.presentation.ui.backlog_flow.dialog

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.LevelType
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.base.BaseBottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_new_task.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class NewTaskDialog : BaseBottomSheetDialogFragment() {

    private val viewModel: NewTaskViewModel by viewModel()

    companion object {
        fun newInstance() = NewTaskDialog()
    }

    override val resLayout = R.layout.dialog_new_task

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTask.requestFocus()
        initLoader()
        warning()
        tvSave.setOnClickListener { saveTask() }
    }

    private fun saveTask() {
        viewModel.trySaveTask(getTaskModel()).observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
            if (it) {
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val date = sdf.format(System.currentTimeMillis())
                getUpdateCallback()?.onUpdateTask()
            }
        })
    }

    private fun warning() {
        viewModel.warningResult.observe(viewLifecycleOwner, Observer {
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

    private fun initLoader() {
        viewModel.isViewLoading.observe(viewLifecycleOwner, Observer {
            loader.isVisible = it
        })
    }

    private fun getUpdateCallback(): UpdateBacklogCallback? = targetFragment as? UpdateBacklogCallback

}