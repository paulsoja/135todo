package com.paulsoia.todo135.presentation.ui.settings_flow.settings.theme.items

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.setting.ThemeColor
import com.paulsoia.todo135.presentation.base.BaseViewHolder
import com.paulsoia.todo135.presentation.utils.inflate

class ThemeAdapter : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private val items = mutableListOf<ThemeColor>()

    private val colorViewHolder = ColorViewHolder

    internal var callback: ThemeChangeListener? = null
        set(value) {
            field = value
            colorViewHolder.callbackColor = { theme -> callback?.onColorClicked(theme) }
        }

    internal fun swapData(list: List<ThemeColor>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return ColorViewHolder(parent.inflate(R.layout.item_theme_color))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        (holder as? ColorViewHolder)?.bind(items[position])
    }

    override fun getItemCount() = items.size

    interface ThemeChangeListener {
        fun onColorClicked(theme: ThemeColor)
    }

}