package com.paulsoia.todo135.presentation.ui.todo_flow.days.dialogs

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulsoia.todo135.R
import com.paulsoia.todo135.presentation.base.BaseBottomSheetDialogFragment
import com.paulsoia.todo135.presentation.ui.todo_flow.days.dialogs.items.ImportAdapter
import kotlinx.android.synthetic.main.dialog_import_task.*

class ImportTaskDialog : BaseBottomSheetDialogFragment() {

    companion object {
        fun newInstance() = ImportTaskDialog()
    }

    override val resLayout = R.layout.dialog_import_task

    private val adapter = ImportAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initRecyclerView()
    }

    private fun initView() {
        holderEmptyState.isVisible = true
        rvImportTask.isVisible = false
    }

    private fun initRecyclerView() {
        with(rvImportTask) {
            layoutManager = LinearLayoutManager(requireContext())
            /*val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            addItemDecoration(divider)*/
            adapter = this@ImportTaskDialog.adapter
        }
    }

}