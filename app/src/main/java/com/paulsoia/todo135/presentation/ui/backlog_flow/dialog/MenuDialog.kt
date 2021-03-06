package com.paulsoia.todo135.presentation.ui.backlog_flow.dialog

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.LevelType
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.base.BaseBottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_menu.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class MenuDialog : BaseBottomSheetDialogFragment() {

    companion object {
        private const val TASK_ARG = "task"
        private const val TASK_MENU = "menu"

        fun newInstance(task: Task, isMove: Boolean = false) = MenuDialog().apply {
            arguments = bundleOf(TASK_ARG to task, TASK_MENU to isMove)
        }
    }

    private val menuViewModel: MenuViewModel by viewModel()

    override val resLayout = R.layout.dialog_menu

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val task = arguments?.getParcelable<Task>(TASK_ARG)
        val isMove = arguments?.getBoolean(TASK_MENU) ?: false
        task?.let { tsk ->
            tvYesterday.setOnClickListener { setDateAndUpdate(tsk, -1) }
            tvToday.setOnClickListener { setDateAndUpdate(tsk, 0) }
            tvTomorrow.setOnClickListener { setDateAndUpdate(tsk, +1) }
            tvBig.setOnClickListener {
                tsk.level = LevelType.BIG
                menuViewModel.tryUpdateTask(tsk)
            }
            tvMedium.setOnClickListener {
                tsk.level = LevelType.MEDIUM
                menuViewModel.tryUpdateTask(tsk)
            }
            tvSmall.setOnClickListener {
                tsk.level = LevelType.SMALL
                menuViewModel.tryUpdateTask(tsk)
            }
        }
        closeDialog(isMove, task?.id)
    }

    private fun setDateAndUpdate(task: Task, day: Int) {
        val date = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        date.add(Calendar.DATE, day)
        val unixtime = date.time.time.div(1000)
        task.date = unixtime.toInt()
        llDates.isVisible = false
        llLevels.isVisible = true
        //menuViewModel.tryUpdateTask(task)
    }

    private fun closeDialog(isMove: Boolean = false, taskId: Long?) {
        menuViewModel.updateTaskResult.observe(viewLifecycleOwner, {
            if (!it) Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
            else {
                if (isMove) {
                    taskId?.let { id-> menuViewModel.deleteTaskById(id).observe(viewLifecycleOwner,
                        {
                            Toast.makeText(requireContext(), "$it", Toast.LENGTH_SHORT).show()
                        })}
                }
            }
            getUpdateCallback()?.onUpdateTask()
            dismiss()
        })
    }

    private fun getUpdateCallback(): UpdateBacklogCallback? = targetFragment as? UpdateBacklogCallback

}