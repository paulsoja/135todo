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
import com.paulsoia.todo135.presentation.ui.todo_flow.todo.dialog.TaskBottomDialogFragment
import com.paulsoia.todo135.presentation.utils.onClick
import kotlinx.android.synthetic.main.fragment_backlog.*
import kotlinx.android.synthetic.main.item_days.fabAdd
import org.koin.androidx.viewmodel.ext.android.viewModel

class BacklogFragment : BaseFragment() {

    companion object {
        fun newInstance() = BacklogFragment()
    }

    private val backlogViewModel: BacklogViewModel by viewModel()

    override val layoutRes: Int = R.layout.fragment_backlog

    private val taskItems = mutableListOf<Task>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backlogViewModel.getAllTasks()
        getTasks()
        initRecyclerView()
        fabAdd.onClick {
            TaskBottomDialogFragment.newInstance().apply {
                setTargetFragment(this@BacklogFragment, 0)
            }.show(parentFragmentManager, "tag")
        }
    }

    private fun initRecyclerView() {
        rvTasks.layoutManager = LinearLayoutManager(requireContext())
        val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        rvTasks.addItemDecoration(divider)
        rvTasks.adapter = BacklogTaskAdapter(taskItems)
    }

    private fun getTasks() {
        backlogViewModel.result.observe(viewLifecycleOwner, Observer {
            taskItems.addAll(it)
        })
    }

}