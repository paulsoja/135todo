package com.paulsoia.todo135.presentation.ui.todo_flow.todo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.base.BaseFragment
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.EditTaskDialog
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.NewTaskDialog
import com.paulsoia.todo135.presentation.ui.todo_flow.days.items.TodoDayAdapter
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.android.synthetic.main.toolbar_todo.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class TodoFragment : BaseFragment(), TodoDayAdapter.TaskListener /*ViewPagerAdapter.UpdatePageCallback*/ {

    companion object {
        fun newInstance() = TodoFragment()
    }

    private val viewModel: TodoViewModel by viewModel()

    override val layoutRes: Int = R.layout.fragment_todo
    private val adapter = TodoDayAdapter()

    private var items = listOf<Task>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLoader()
        initRecyclerView()
        setupTabs()
        viewModel.getTaskWithDate()
        ivSwap.setOnClickListener { Toast.makeText(requireContext(), "swap", Toast.LENGTH_SHORT).show() }
        tvToday.text = getDate(1, true)
        getTasks()
    }

    private fun initRecyclerView() {
        adapter.callback = this
        rvTodoDay.layoutManager = LinearLayoutManager(requireContext())
        rvTodoDay.adapter = adapter
    }

    private fun setupTabs() {
        tabs.addTab(tabs.newTab().setText(getString(R.string.text_yesterday)))
        tabs.addTab(tabs.newTab().setText(getString(R.string.text_today)))
        tabs.addTab(tabs.newTab().setText(getString(R.string.text_tomorrow)))
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val currentTab = tab?.text
                Toast.makeText(requireContext(), "$currentTab", Toast.LENGTH_SHORT).show()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun getDate(position: Int, isShowDayName: Boolean): String {
        val date = Calendar.getInstance()
        val sdf = when (isShowDayName) {
            true -> SimpleDateFormat("EEEE dd/MM/yyyy", Locale.getDefault())
            false -> SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        }
        when (position) {
            0 -> date.add(Calendar.DATE, -1)
            1 -> date.add(Calendar.DATE, 0)
            2 -> date.add(Calendar.DATE, +1)
            else -> date.add(Calendar.DATE, -100)
        }
        return sdf.format(date.time)
    }

    private fun getTasks() {
        viewModel.resultTasks.observe(viewLifecycleOwner, {
            adapter.swapData(it.first)
        })
    }

    private fun initLoader() {
        viewModel.isViewLoading.observe(viewLifecycleOwner, {
            //loader.isVisible = it
        })
    }

    override fun onCheckboxClick(task: Task) = viewModel.updateTask(task)

    override fun onItemClick(task: Task) {
        fragmentManager?.let {
            EditTaskDialog.newInstance(task).apply {
                setTargetFragment(this@TodoFragment, 1)
            }.show(it, "edit")
        }
    }

    override fun onEmptyItemClick() {
        fragmentManager?.let {
            NewTaskDialog.newInstance().apply {
                setTargetFragment(this@TodoFragment, 2)
            }.show(it, "create")
        }
    }

}