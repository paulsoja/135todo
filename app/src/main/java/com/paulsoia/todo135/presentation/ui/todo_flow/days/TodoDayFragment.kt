package com.paulsoia.todo135.presentation.ui.todo_flow.days

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.model.task.TaskMarker
import com.paulsoia.todo135.presentation.base.BaseFragment
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.EditTaskDialog
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.NewTaskDialog
import com.paulsoia.todo135.presentation.ui.todo_flow.days.import_task.ImportDialog
import com.paulsoia.todo135.presentation.ui.todo_flow.days.items.TodoDayAdapter
import kotlinx.android.synthetic.main.fragment_todo_day.*
import org.koin.android.ext.android.inject

class TodoDayFragment : BaseFragment(), TodoDayAdapter.TaskListener,
    NewTaskDialog.OpenImportCallback, ImportDialog.GetTaskAndCloseListener {

    companion object {
        private const val ITEMS = "items"

        var callback: ((task: Task) -> Unit)? = null

        fun newInstance(items: List<TaskMarker>) = TodoDayFragment()
            .apply { arguments = bundleOf(ITEMS to items) }
    }

    private val viewModel: TodoDayViewModel by inject()

    override val layoutRes = R.layout.fragment_todo_day

    private val adapter = TodoDayAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.items.value = arguments?.getParcelableArrayList<Task>(ITEMS)
        initRecyclerView()
        setListData()
        openImport()
    }

    private fun initRecyclerView() {
        adapter.callback = this
        rvTodoDay.layoutManager = LinearLayoutManager(requireContext())
        rvTodoDay.adapter = adapter
    }

    private fun setListData() = viewModel.items.observe(viewLifecycleOwner, { adapter.swapData(it) })

    override fun onCheckboxClick(task: Task) = viewModel.updateTask(task)

    override fun onItemClick(task: Task) {
        fragmentManager?.let {
            EditTaskDialog.newInstance(task).apply {
                setTargetFragment(this@TodoDayFragment, 1)
            }.show(it, "edit")
        }
    }

    override fun onUpdateTask(task: Task?) {
        task?.let { tsk ->
            viewModel.getTaskById(tsk).observe(viewLifecycleOwner, { tskRes ->
                val result = viewModel.items.value
                result?.find { itm ->
                    (itm as? Task)?.id == tskRes.id && tskRes.id != null
                }?.let { tm ->
                    (tm as? Task)?.isComplete = task.isComplete
                    (tm as? Task)?.message = task.message
                    adapter.updateItemById(tm)
                }
            })
            callback?.invoke(tsk)
        }
    }

    private fun openNewTaskDialog(taskMessage: String? = null) {
        fragmentManager?.let {
            NewTaskDialog.newInstance(taskMessage).apply {
                setTargetFragment(this@TodoDayFragment, 2)
            }.show(it, "create")
        }
    }

    override fun onEmptyItemClick() {
        openNewTaskDialog()
    }

    private fun openImport() {
        viewModel.tasks.observe(viewLifecycleOwner, { list ->
            fragmentManager?.let {
                ImportDialog.newInstance(list).apply {
                    setTargetFragment(this@TodoDayFragment, 3)
                }.show(it, "import")
            }
        })
    }

    override fun onImportClicked() {
        viewModel.getAllTasks()
    }

    override fun closeDialog(task: Task) {
        openNewTaskDialog(task.message)
    }

}