package com.paulsoia.todo135.presentation.ui.backlog_flow.backlog

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulsoia.todo135.presentation.base.BaseFragment
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.ui.backlog_flow.backlog.items.BacklogTaskAdapter
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.*
import com.paulsoia.todo135.presentation.utils.onClick
import kotlinx.android.synthetic.main.fragment_backlog.*
import kotlinx.android.synthetic.main.toolbar_backlog.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BacklogFragment : BaseFragment(), BacklogTaskAdapter.TaskListener, UpdateBacklogCallback {

    companion object {
        fun newInstance() = BacklogFragment()
    }

    private val viewModel: BacklogViewModel by viewModel()
    private val adapter = BacklogTaskAdapter()

    override val layoutRes: Int = R.layout.fragment_backlog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.callback = this
        viewModel.getAllTasks()
        getTasks()
        updateTasks()
        initRecyclerView()
        ivSort.onClick { sortMenu(it) }
        ivFilter.onClick { filterMenu(it) }
        fabAdd.onClick {
            NewTaskDialog.newInstance().apply {
                setTargetFragment(this@BacklogFragment, 0)
            }.show(parentFragmentManager, "new")
        }
    }

    private fun initRecyclerView() {
        with(rvTasks) {
            layoutManager = LinearLayoutManager(requireContext())
            val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            addItemDecoration(divider)
            adapter = this@BacklogFragment.adapter
        }

        //todo for next release
        /*val helper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP.or(ItemTouchHelper.DOWN), 0) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val positionDragged = viewHolder.adapterPosition
                val positionTarget = target.adapterPosition
                Collections.swap(data, positionDragged, positionTarget)
                adapter.notifyItemMoved(positionDragged, positionTarget)
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            }

        })
        helper.attachToRecyclerView(rvTasks)*/
    }

    private fun getTasks() {
        viewModel.result.observe(viewLifecycleOwner, Observer {
            val result = it.filter {
                when (viewModel.getFilterType()) {
                    "tag" -> (it as? Task)?.tag != null
                    "date" -> (it as? Task)?.date != null
                    else -> (it as? Task)?.message != ""
                }
            }
            adapter.swapData(result)
        })
    }

    private fun updateTasks() {
        viewModel.resultUpdate.observe(viewLifecycleOwner, Observer {
            if (it) viewModel.getAllTasks()
        })
    }

    override fun onCheckboxClicked(task: Task, position: Int) {
        viewModel.updateTask(task)
    }

    override fun onTaskClicked(task: Task, position: Int) {
        EditTaskDialog.newInstance(task, position).apply {
            setTargetFragment(this@BacklogFragment, 0)
        }.show(parentFragmentManager, "edit")
    }

    override fun onMenuClicked(task: Task, position: Int, v: View) {
        val popup = PopupMenu(requireContext(), v)
        popup.inflate(R.menu.popup_menu)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menuCopy -> menuTask(task, false)
                R.id.menuMove -> menuTask(task, true)
                R.id.menuReset -> resetTask(task)
                R.id.menuDelete -> deleteTask(task)
            }
            return@setOnMenuItemClickListener true
        }
        popup.show()
    }

    override fun onTagClicked(task: Task, position: Int) {
        TagDialog.newInstance(task).apply {
            setTargetFragment(this@BacklogFragment, 0)
        }.show(parentFragmentManager, "tag")
    }

    private fun sortMenu(v: View) {
        PopupMenu(requireContext(), v).apply {
            inflate(R.menu.sort_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.itemName -> sortByValue("name")
                    R.id.itemTag -> sortByValue("tag")
                    R.id.itemDate -> sortByValue("date")
                    R.id.itemReset -> sortByValue("reset")
                }
                return@setOnMenuItemClickListener true
            }
        }.show()
    }

    private fun sortByValue(value: String) {
        viewModel.result.observe(viewLifecycleOwner, Observer {
            val result = it.filter {
                when(value) {
                    "name" -> (it as? Task)?.message != null
                    "tag" -> (it as? Task)?.tag != null
                    "date" -> (it as? Task)?.date != null
                    else -> (it as? Task)?.message != ""
                }
            }
            viewModel.saveSortType(value)
            adapter.swapData(result)
        })
    }

    private fun filterMenu(v: View) {
        PopupMenu(requireContext(), v).apply {
            inflate(R.menu.filter_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.itemTag -> filterByValue("tag")
                    R.id.itemDate -> filterByValue("date")
                    R.id.itemAll -> filterByValue("all")
                }
                return@setOnMenuItemClickListener true
            }
        }.show()
    }

    private fun filterByValue(value: String) {
        viewModel.result.observe(viewLifecycleOwner, Observer {
            val result = it.filter {
                when(value) {
                    "tag" -> (it as? Task)?.tag != null
                    "date" -> (it as? Task)?.date != null
                    else -> (it as? Task)?.message != ""
                }
            }
            viewModel.saveFilterType(value)
            adapter.swapData(result)
        })
    }

    private fun resetTask(task: Task) {
        task.isComplete = false
        task.date = ""
        task.level = ""
        viewModel.updateTask(task)
        updateTasks()
    }

    private fun deleteTask(task: Task) {
        viewModel.deleteTask((task))
        updateTasks()
    }

    private fun menuTask(task: Task, isMove: Boolean) {
        /*val date = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        date.add(Calendar.DATE, 0)
        task.date = sdf.format(date.time)
        backlogViewModel.updateTask(task)*/
        MenuDialog.newInstance(task, isMove).apply {
            setTargetFragment(this@BacklogFragment, 0)
        }.show(parentFragmentManager, "new")
    }

    override fun onUpdateTask() {
        viewModel.getAllTasks()
    }

}