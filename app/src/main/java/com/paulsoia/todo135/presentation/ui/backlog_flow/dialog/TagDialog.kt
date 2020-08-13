package com.paulsoia.todo135.presentation.ui.backlog_flow.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.tag.Tag
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.base.BaseBottomSheetDialogFragment
import com.paulsoia.todo135.presentation.ui.backlog_flow.dialog.items.TagAdapter
import kotlinx.android.synthetic.main.dialog_add_tag.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TagDialog : BaseBottomSheetDialogFragment(), TagAdapter.TagListener {

    private val viewModel: TagViewModel by viewModel()

    companion object {
        private const val TASK_ARG = "task"

        fun newInstance(task: Task) = TagDialog().apply {
            arguments = bundleOf(TASK_ARG to task)
        }
    }

    override val resLayout = R.layout.dialog_add_tag

    private val adapter = TagAdapter()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as? BottomSheetDialog
        bottomSheetDialog?.setOnShowListener {
            val dialog = it as? BottomSheetDialog
            val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                BottomSheetBehavior.from(it).state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return bottomSheetDialog ?: super.onCreateDialog(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter.callback = this
        arguments?.let {
            viewModel.message.value = it.takeIf { it.containsKey(TASK_ARG) }
                ?.getParcelable(TASK_ARG)
                ?: throw IllegalArgumentException("`${Task::class.java.simpleName}` required")
        }
        initAdapter()
        getTags()
        viewModel.getAllTags()
        ivSave.setOnClickListener { saveNewTag() }
        btnCancel.setOnClickListener { dismiss() }
        btnOk.setOnClickListener { viewModel.updateTask(viewModel.message.value!!).observe(viewLifecycleOwner, Observer {
            if (it) dismiss()
        }) }
    }

    private fun saveNewTag() {
        val tag = etNewTag.text.toString()
        viewModel.saveNewTag(tag, 0).observe(viewLifecycleOwner, Observer {
            if (it) viewModel.getAllTags()
        })
    }

    private fun initAdapter() {
        with(rvTags) {
            layoutManager = LinearLayoutManager(requireContext())
            val divider = DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
            addItemDecoration(divider)
            adapter = this@TagDialog.adapter
        }
    }

    private fun getTags() {
        viewModel.result.observe(viewLifecycleOwner, Observer {
            it.find {
                (it as? Tag)?.tag == viewModel.message.value?.tag && viewModel.message.value?.tag != ""
            }?.let {
                (it as? Tag)?.isChecked = true
            }
            adapter.swapData(it)
        })
    }

    override fun onTagClicked(tag: Tag, position: Int) {
        viewModel.result.observe(viewLifecycleOwner, Observer { list ->
            list.forEachIndexed { index, tagMarker ->
                if((tagMarker as Tag).isChecked) {
                    tagMarker.isChecked = false
                    adapter.updateItem(tagMarker, index)
                }
                if (tagMarker.tag == tag.tag) {
                    viewModel.message.value?.let { it.tag = tag.tag }
                    tagMarker.isChecked = true
                    adapter.updateItem(tagMarker, index)
                }
            }
            /*list.find { (it as? Tag)?.tag == tag.tag }?.let {
                viewModel.message.value?.let { it.tag = tag.tag }
                (it as? Tag)?.isChecked = true
            }.also {
                adapter.swapData(list)
            }*/
        })
    }

}