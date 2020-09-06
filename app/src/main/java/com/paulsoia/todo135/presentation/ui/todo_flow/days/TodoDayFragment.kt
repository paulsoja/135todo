package com.paulsoia.todo135.presentation.ui.todo_flow.days

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.model.task.TaskMarker
import com.paulsoia.todo135.presentation.base.BaseFragment
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.EditTaskDialog
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.UpdateBacklogCallback
import com.paulsoia.todo135.presentation.ui.todo_flow.days.items.ListViewHolder
import com.paulsoia.todo135.presentation.ui.todo_flow.days.items.TodoDayAdapter
import kotlinx.android.synthetic.main.fragment_todo_day.*
import org.koin.android.ext.android.inject

class TodoDayFragment : BaseFragment(), TodoDayAdapter.TaskListener, UpdateBacklogCallback {

    companion object {
        private const val ITEMS = "items"

        var callback: ((task: Task) -> Unit)? = null

        fun newInstance(items: List<TaskMarker>) = TodoDayFragment()
            .apply { arguments = bundleOf(ITEMS to items) }
    }

    private val touchHelper by lazy { switchItems() }

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
        rvTodoDay.adapter = adapter
        touchHelper.attachToRecyclerView(rvTodoDay)
    }

    private fun switchItems(): ItemTouchHelper {
        return ItemTouchHelper(object : SimpleCallback(UP or DOWN, 0) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val adapter = recyclerView.adapter as TodoDayAdapter
                if (viewHolder is ListViewHolder && target is ListViewHolder) {
                    val from = viewHolder.adapterPosition
                    val to = target.adapterPosition
                    adapter.moveItem(from, to)
                    adapter.notifyItemMoved(from, to)
                    return true
                }
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if (actionState == ACTION_STATE_DRAG) {
                    if (viewHolder is ListViewHolder) {
                        viewHolder.itemView.alpha = 0.5f
                    }
                }
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
                if (viewHolder is ListViewHolder) {
                    viewHolder.itemView.alpha = 1.0f
                }
            }
        })
    }

    private fun setListData() = viewModel.items.observe(viewLifecycleOwner, Observer {
        adapter.swapData(it)
    })

    override fun onCheckboxClick(task: Task) = viewModel.updateTask(task)

    override fun onDragItem(viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is ListViewHolder) {
            touchHelper.startDrag(viewHolder)
        }
    }

    override fun onItemClick(task: Task) {
        EditTaskDialog.newInstance(task).apply {
            setTargetFragment(this@TodoDayFragment, 0)
        }.show(parentFragmentManager, "edit")
    }

    override fun onUpdateTask(task: Task?) {
        task?.let { tsk ->
            viewModel.getTaskById(tsk).observe(viewLifecycleOwner, Observer { tskRes ->
                val result = viewModel.items.value
                result?.find {itm ->
                    (itm as? Task)?.id == tskRes.id && tskRes.id != null
                }?.let { tm ->
                    (tm as? Task)?.isComplete = task.isComplete
                    (tm as? Task)?.message = task.message
                    adapter.updateItemById(tm)
                }
            })
        }
        task?.let {
            callback?.invoke(it)
        }

    }



}