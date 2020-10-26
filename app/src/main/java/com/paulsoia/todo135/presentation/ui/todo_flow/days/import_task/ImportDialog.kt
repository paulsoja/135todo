package com.paulsoia.todo135.presentation.ui.todo_flow.days.import_task

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.base.BaseBottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_import_task.*
import kotlinx.android.synthetic.main.toolbar_title_with_close.*

class ImportDialog : BaseBottomSheetDialogFragment(), ImportAdapter.TaskListener {

    companion object {
        private const val ITEMS_ARG = "items_arg"

        fun newInstance(items: List<Task>) = ImportDialog().apply {
            arguments = bundleOf(ITEMS_ARG to items)
        }
    }

    override val resLayout = R.layout.fragment_import

    private val adapter = ImportAdapter()
    private var items = listOf<Task>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            items = it.takeIf { it.containsKey(ITEMS_ARG) }?.getParcelableArrayList<Task>(ITEMS_ARG) as List<Task>
        }
        initRecyclerView()
        setData()
        ivClose.setOnClickListener { dismiss() }
        adapter.callback = this
    }

    private fun initRecyclerView() {
        with(rvImportTask) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@ImportDialog.adapter
        }
    }

    private fun setData() {
        adapter.swapData(items)
    }

    override fun onTaskClicked(task: Task) {
        getTaskCallback()?.closeImportDialog(task).also { dismiss() }
    }

    private fun getTaskCallback(): GetTaskAndCloseListener? = targetFragment as? GetTaskAndCloseListener

    interface GetTaskAndCloseListener {
        fun closeImportDialog(task: Task)
    }

}