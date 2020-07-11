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
import com.paulsoia.todo135.presentation.ui.backlog_flow.backlog.list.BacklogTaskAdapter
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.EditTaskDialog
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.MenuDialog
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.NewTaskDialog
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.UpdateBacklogCallback
import com.paulsoia.todo135.presentation.utils.onClick
import kotlinx.android.synthetic.main.fragment_backlog.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BacklogFragment : BaseFragment(), BacklogTaskAdapter.Callback, UpdateBacklogCallback {

    companion object {
        fun newInstance() = BacklogFragment()
    }

    private val backlogViewModel: BacklogViewModel by viewModel()
    private val adapter = BacklogTaskAdapter()
    var data = listOf<Task>()

    override val layoutRes: Int = R.layout.fragment_backlog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.callback = this
        backlogViewModel.getAllTasks()
        getTasks()
        //updateAdapter()
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
        backlogViewModel.result.observe(viewLifecycleOwner, Observer {
            adapter.swapData(it)
            data = it
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

    override fun onMenuClicked(task: Task, position: Int, v: View) {
        val popup = PopupMenu(requireContext(), v)
        popup.inflate(R.menu.popup_menu)
        popup.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menuCopy -> menuTask(task, false)
                R.id.menuMove -> menuTask(task, true)
                R.id.menuReset -> Toast.makeText(requireContext(), "reset", Toast.LENGTH_SHORT).show()
                R.id.menuDelete -> Toast.makeText(requireContext(), "delete", Toast.LENGTH_SHORT).show()
            }
            return@setOnMenuItemClickListener true
        }
        popup.show()
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

    override fun onUpdateTask(date: String) {
        backlogViewModel.getAllTasks()
    }

}