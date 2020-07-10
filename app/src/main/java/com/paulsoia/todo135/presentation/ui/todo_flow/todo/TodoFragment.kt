package com.paulsoia.todo135.presentation.ui.todo_flow.todo

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.base.BaseFragment
import com.paulsoia.todo135.presentation.ui.todo_flow.todo.items.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.android.synthetic.main.toolbar_todo.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TodoFragment : BaseFragment() {

    companion object {
        fun newInstance() = TodoFragment()
    }

    private val todoViewModel: TodoViewModel by viewModel()

    override val layoutRes: Int = R.layout.fragment_todo

    internal lateinit var viewpageradapter: ViewPagerAdapter
    private var items = listOf<Task>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabs()
        initLoader()
        todoViewModel.getTaskWithDate()
        getTasks()
        tvToday.text = getDate(1, true)
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

    private fun setupTabs() {
        viewpageradapter =
            ViewPagerAdapter(
                childFragmentManager, requireContext()
            )
        viewPager.adapter = viewpageradapter
        tabs.setupWithViewPager(viewPager)
        viewPager.currentItem = 1 //today tab
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                viewpageradapter.notifyDataSetChanged()
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tvToday.text = getDate(tab?.position ?: 100, true)
            }
        })
    }

    private fun getTasks() {
        todoViewModel.resultTasks.observe(viewLifecycleOwner, Observer {
            items = it
            viewpageradapter.swapData(items)
        })
    }

    private fun initLoader() {
        todoViewModel.isViewLoading.observe(viewLifecycleOwner, Observer {
            //loader.isVisible = it
        })
    }

}