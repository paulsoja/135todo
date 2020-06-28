package com.paulsoia.todo135.presentation.ui.todo_flow.todo.items

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.paulsoia.todo135.R

class ViewPagerAdapter(fm: FragmentManager, private val context: Context) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val ITEM_SIZE = 3

    override fun getItem(position: Int): Fragment {
        return TodoItemFragment.newInstance(position)
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