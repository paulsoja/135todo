package com.paulsoia.todo135.presentation.ui.todo_flow.days

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.task.LevelType
import com.paulsoia.todo135.business.model.task.Task
import com.paulsoia.todo135.business.model.task.TaskMarker
import com.paulsoia.todo135.business.model.task.Title
import java.text.SimpleDateFormat
import java.util.*

class ViewPagerAdapter(fm: FragmentManager, private val context: Context) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val ITEM_SIZE = 3
    private val items = mutableListOf<TaskMarker>()

    fun swapData(list: List<TaskMarker>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
        return TodoDayFragment.newInstance(getTasksByDate(position))
    }

    private fun getTasksByDate(position: Int): List<TaskMarker> {
        val result = mutableListOf<TaskMarker>()
        val date = Calendar.getInstance()
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        when(position) {
            0 -> date.add(Calendar.DATE, -1)
            1 -> date.add(Calendar.DATE, 0)
            2 -> date.add(Calendar.DATE, +1)
            else -> date.add(Calendar.DATE, -100)
        }

        val filterList = items.filter { (it as Task).date == sdf.format(date.time) }

        val big: MutableList<TaskMarker> = filterList.filter { (it as Task).level == LevelType.BIG }.take(1).toMutableList()
        if (big.isEmpty()) {
            big.add(Task.empty(level = LevelType.BIG))
        }

        val medium: MutableList<TaskMarker> = filterList.filter { (it as Task).level == LevelType.MEDIUM }.take(3).toMutableList()
        if (medium.size < 3) {
            for (i in 1..3-medium.size) {
                medium.add(Task.empty(level = LevelType.MEDIUM))
            }
        }

        val small: MutableList<TaskMarker> = filterList.filter { (it as Task).level == LevelType.SMALL }.take(3).toMutableList()
        if (small.size < 3) {
            for (i in 1..5-small.size) {
                small.add(Task.empty(level = LevelType.SMALL))
            }
        }

        val statTitleBig = big.filter { (it as Task).isComplete }.size
        val statTitleMedium = medium.filter { (it as Task).isComplete }.size
        val statTitleSmall = small.filter { (it as Task).isComplete }.size

        result.add(Title("big $statTitleBig / 1"))
        result.addAll(big)
        result.add(Title("medium $statTitleMedium / 3"))
        result.addAll(medium)
        result.add(Title("small $statTitleSmall / 5"))
        result.addAll(small)

        return result
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