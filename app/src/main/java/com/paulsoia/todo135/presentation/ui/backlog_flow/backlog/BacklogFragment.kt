package com.paulsoia.todo135.presentation.ui.backlog_flow.backlog

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulsoia.todo135.presentation.base.BaseFragment
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.ui.backlog_flow.backlog.list.BacklogTaskAdapter
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.EditTaskDialog
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.NewTaskDialog
import com.paulsoia.todo135.presentation.utils.onClick
import kotlinx.android.synthetic.main.fragment_backlog.*
import kotlinx.android.synthetic.main.item_days.fabAdd
import org.koin.androidx.viewmodel.ext.android.viewModel

class BacklogFragment : BaseFragment(), BacklogTaskAdapter.Callback {

    companion object {
        fun newInstance() = BacklogFragment()
    }

    private val backlogViewModel: BacklogViewModel by viewModel()
    private val adapter = BacklogTaskAdapter()

    override val layoutRes: Int = R.layout.fragment_backlog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.callback = this
        backlogViewModel.getAllTasks()
        getTasks()
        initRecyclerView()
        fabAdd.onClick {
            NewTaskDialog.newInstance().apply {
                setTargetFragment(this@BacklogFragment, 0)
            }.show(parentFragmentManager, "new")
        }
    }

    private fun initRecyclerView() {
        rvTasks.layoutManager = LinearLayoutManager(requireContext())
        val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        rvTasks.addItemDecoration(divider)
        rvTasks.adapter = adapter
    }

    private fun getTasks() {
        backlogViewModel.result.observe(viewLifecycleOwner, Observer {
            adapter.swapData(it.sortedBy { it.isComplete })
        })
    }

    private fun updateAdapter() {
        backlogViewModel.resultUpdate.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })
    }

    override fun onCheckboxClicked(task: Task, position: Int) {
        backlogViewModel.updateTask(task)
    }

    override fun onTaskClicked(task: Task, position: Int) {
        EditTaskDialog.newInstance(task, position).apply {
            setTargetFragment(this@BacklogFragment, 0)
        }.show(parentFragmentManager, "edit")
    }

}