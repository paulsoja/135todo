package com.paulsoia.todo135.presentation.ui.todo_flow.todo.items

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.paulsoia.todo135.presentation.base.BaseFragment
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.utils.onClick
import kotlinx.android.synthetic.main.item_days.*
import org.koin.android.ext.android.inject

class TodoItemFragment : BaseFragment() {

    companion object {
        private val ARGS = "args"
        private const val ITEMS = "items"

        fun newInstance(count: Int, items: ArrayList<Task>) = TodoItemFragment()
            .apply {
                arguments = bundleOf(ARGS to count, ITEMS to items)
            }
    }

    private val todoItemViewModel: TodoItemViewModel by inject()

    private var count: Int = 111
    private var items = arrayListOf<Task>()

    override val layoutRes: Int = R.layout.item_days

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        count = arguments?.getInt(ARGS) as Int
        items = arguments?.getParcelableArrayList<Task>(ITEMS) as ArrayList<Task>
        btnOne.onClick {
            etOne.isEnabled = true
            btnOne.setImageResource(R.drawable.ic_check)
        }
        getTaskList()
        check()
    }

    private fun getTaskList() {
        todoItemViewModel.result.observe(viewLifecycleOwner, Observer {

        })
    }

    private fun initViews() {

    }

    private fun toggleEditText(editText: EditText, imageView: ImageView, position: Int) {
        //editText.setText(items[position].message)
        imageView.onClick {
            if (editText.isEnabled) {
                editText.isEnabled = false
                imageView.setImageResource(R.drawable.ic_edit)
            } else {
                editText.isEnabled = true
                imageView.setImageResource(R.drawable.ic_check)
            }
        }
    }

    private fun check() {
        toggleEditText(etOne, btnOne, 0)
        toggleEditText(etTwo, btnTwo, 1)
        toggleEditText(etThree, btnThree, 2)
        toggleEditText(etFour, btnFour, 3)
        toggleEditText(etFive, btnFive, 4)
        toggleEditText(etSix, btnSix, 5)
        toggleEditText(etSeven, btnSeven, 6)
        toggleEditText(etEight, btnEight, 7)
        toggleEditText(etNine, btnNine, 8)
    }

}