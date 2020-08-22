package com.paulsoia.todo135.presentation.ui.settings_flow.settings.theme.items

import android.graphics.Color
import android.view.View
import com.paulsoia.todo135.R
import com.paulsoia.todo135.business.model.setting.ThemeBaseType
import com.paulsoia.todo135.business.model.setting.ThemeColor
import com.paulsoia.todo135.presentation.base.BaseViewHolder
import kotlinx.android.synthetic.main.item_theme_color.view.*

class ColorViewHolder(view: View) : BaseViewHolder<ThemeColor>(view) {

    companion object {
        var callbackColor: ((theme : ThemeColor) -> Unit)? = null
    }

    override fun bind(item: ThemeColor) {
        with(itemView) {
            when(item.color) {
                ThemeBaseType.BLUE -> ivColor.setColorFilter(R.color.main_blue)
                ThemeBaseType.INDIGO -> ivColor.setColorFilter(R.color.main_indigo)
                ThemeBaseType.GREEN -> ivColor.setColorFilter(R.color.green)
                ThemeBaseType.RED -> ivColor.setColorFilter(R.color.red)
            }
            tvColor.text = item.name
            setOnClickListener { callbackColor?.invoke(item) }
        }
    }

}