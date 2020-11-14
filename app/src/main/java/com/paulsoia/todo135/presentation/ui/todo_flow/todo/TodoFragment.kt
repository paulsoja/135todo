package com.paulsoia.todo135.presentation.ui.todo_flow.todo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.LevelType
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.base.BaseFragment
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.EditTaskDialog
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.UpdateBacklogCallback
import com.paulsoia.todo135.presentation.ui.todo_flow.days.NewTaskTodoDialog
import com.paulsoia.todo135.presentation.ui.todo_flow.days.import_task.ImportDialog
import com.paulsoia.todo135.presentation.ui.todo_flow.days.items.TodoDayAdapter
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.android.synthetic.main.toolbar_todo.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class TodoFragment : BaseFragment(), TodoDayAdapter.TaskListener,
    ImportDialog.GetTaskAndCloseListener, UpdateBacklogCallback {

    companion object {
        fun newInstance() = TodoFragment()
    }

    private val viewModel: TodoViewModel by viewModel()

    override val layoutRes: Int = R.layout.fragment_todo
    private val adapter = TodoDayAdapter()
    private var positionClicked: Int = -1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLoader()
        initRecyclerView()
        setupTabs()
        viewModel.getTaskWithDate()
        ivSwap.setOnClickListener { Toast.makeText(requireContext(), "swap", Toast.LENGTH_SHORT).show() }
        tvToday.text = getDate(1, true)
        getTasks()
        openImport()
        updateList()
        updateTasks()
    }

    private fun initRecyclerView() {
        adapter.callback = this
        rvTodoDay.layoutManager = LinearLayoutManager(requireContext())
        rvTodoDay.adapter = adapter
    }

    private fun updateTasks() {
        viewModel.resultSaveTask.observe(viewLifecycleOwner, {
            if (it) { viewModel.getTaskWithDate() }
        })
    }

    private fun setupTabs() {
        tabs.addTab(tabs.newTab().setText(getString(R.string.text_yesterday)))
        tabs.addTab(tabs.newTab().setText(getString(R.string.text_today)))
        tabs.addTab(tabs.newTab().setText(getString(R.string.text_tomorrow)))
        tabs.getTabAt(1)?.select()
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val currentTab = tab?.position
                getTasks(currentTab ?: 1)
                tvToday.text = getDate(tab?.position ?: 1, true)
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

    private fun getTasks(tabPosition: Int = 1) {
        viewModel.resultTasks.observe(viewLifecycleOwner, {
            when (tabPosition) {
                0 -> adapter.swapData(it.first)
                1 -> adapter.swapData(it.second)
                2 -> adapter.swapData(it.third)
            }
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

    override fun onEmptyItemClick(position: Int) {
        positionClicked = position
        viewModel.getAllTasks()
    }

    private fun openImport() {
        viewModel.tasks.observe(viewLifecycleOwner, { list ->
            fragmentManager?.let {
                ImportDialog.newInstance(list).apply {
                    setTargetFragment(this@TodoFragment, 3)
                }.show(it, "import")
            }
        })
    }

    override fun closeImportDialog(task: Task) {
        //save task from import
        val level = when (positionClicked) {
            1 -> LevelType.BIG
            in 3..5 -> LevelType.MEDIUM
            in 7..11 -> LevelType.SMALL
            else -> LevelType.NONE
        }
        val date = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        when (tabs.selectedTabPosition) {
            0 -> date.add(Calendar.DATE, -1)
            1 -> date.add(Calendar.DATE, 0)
            2 -> date.add(Calendar.DATE, +1)
            else -> date.add(Calendar.DATE, -100)
        }
        //date.add(Calendar.DATE, tabs.selectedTabPosition + 1)
        val unixtime = date.time.time.div(1000)
        val itemCopy = task.copy(id = null, date = unixtime.toInt(), level = level)
        viewModel.saveTodoTask(itemCopy)

        /*fragmentManager?.let {
            NewTaskTodoDialog.newInstance(task, positionClicked).apply {
                setTargetFragment(this@TodoFragment, 2)
            }.show(it, "create")
        }*/
    }

    private fun updateList() {

    }

    override fun onUpdateTask(task: Task?) {
        viewModel.getTaskWithDate()
    }

}