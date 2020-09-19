package com.paulsoia.todo135.presentation.ui.todo_flow.todo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.presentation.base.BaseFragment
import com.paulsoia.todo135.presentation.ui.todo_flow.days.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.android.synthetic.main.toolbar_todo.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class TodoFragment : BaseFragment(), ViewPagerAdapter.UpdatePageCallback {

    companion object {
        fun newInstance() = TodoFragment()
    }

    private val todoViewModel: TodoViewModel by viewModel()

    override val layoutRes: Int = R.layout.fragment_todo

    private lateinit var viewpageradapter: ViewPagerAdapter
    private var items = listOf<Task>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLoader()
        todoViewModel.getTaskWithDate().observe(viewLifecycleOwner, {
            //setupTabs()
            getTasks()
        })
        ivSwap.setOnClickListener { Toast.makeText(requireContext(), "swap", Toast.LENGTH_SHORT).show() }
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

    private fun setupTabs(currentTab: Int) {
        viewpageradapter = ViewPagerAdapter(childFragmentManager, requireContext())
        viewpageradapter.callback = this
        viewpageradapter.swapData(items)
        viewPager.adapter = viewpageradapter
        tabs.setupWithViewPager(viewPager)
        viewPager.currentItem = currentTab //today tab
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tvToday.text = getDate(tab?.position ?: 100, true)
            }
        })

    }

    private fun getTasks() {
        todoViewModel.resultTasks.observe(viewLifecycleOwner, {
            items = it
            setupTabs(1)
        })
    }

    private fun initLoader() {
        todoViewModel.isViewLoading.observe(viewLifecycleOwner, {
            //loader.isVisible = it
        })
    }

    override fun onUpdatePage() {
        todoViewModel.getTaskWithDate().observe(viewLifecycleOwner, {
            //setupTabs()
            getTasks()
        })
    }

}