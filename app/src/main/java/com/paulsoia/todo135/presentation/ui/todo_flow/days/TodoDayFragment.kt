package com.paulsoia.todo135.presentation.ui.todo_flow.days

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.model.task.TaskMarker
import com.paulsoia.todo135.presentation.base.BaseFragment
import com.paulsoia.todo135.presentation.ui.todo_flow.days.items.TodoDayAdapter
import kotlinx.android.synthetic.main.fragment_todo_day.*
import org.koin.android.ext.android.inject

class TodoDayFragment : BaseFragment(), TodoDayAdapter.TaskListener {

    companion object {
        private const val ITEMS = "items"

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
    }

    private fun initRecyclerView() {
        adapter.callback = this
        rvTodoDay.layoutManager = LinearLayoutManager(requireContext())
        //val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        //rvTodoDay.addItemDecoration(divider)
        rvTodoDay.adapter = adapter
    }

    private fun setListData() {
        viewModel.items.observe(viewLifecycleOwner, Observer {
            adapter.swapData(it)
        })
    }

    override fun onCheckboxClick(task: Task) {
        viewModel.updateTask(task)
    }

}