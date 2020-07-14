package com.paulsoia.todo135.presentation.ui.todo_flow.todo.items

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.Task
import java.text.SimpleDateFormat
import java.util.*

class ViewPagerAdapter(fm: FragmentManager, private val context: Context) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val ITEM_SIZE = 3
    private val items = arrayListOf<Task>()

    fun swapData(list: List<Task>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return TodoItemFragment.newInstance(position, getTasksByDate(position))
    }

    private fun getTasksByDate(position: Int): ArrayList<Task> {
        val date = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        when(position) {
            0 -> date.add(Calendar.DATE, -1)
            1 -> date.add(Calendar.DATE, 0)
            2 -> date.add(Calendar.DATE, +1)
            else -> date.add(Calendar.DATE, -100)
        }
        val filterList = items.filter { it.date == sdf.format(date.time) }
        return filterList as ArrayList<Task>
    }

    override fun getCount() = ITEM_SIZE

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context.getString(R.string.text_yesterday)
            1 -> context.getString(R.string.text_today)
            2 -> context.getString(R.string.text_tomorrow)
            else -> ""
        }
    }

}