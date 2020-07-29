package com.paulsoia.todo135.presentation.ui.stats_flow.stats

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.paulsoia.todo135.R
import com.paulsoia.todo135.presentation.base.BaseFragment
import com.paulsoia.todo135.presentation.utils.onClick
import kotlinx.android.synthetic.main.fragment_stats.*

class StatsFragment : BaseFragment() {

    companion object {
        fun newInstance() = StatsFragment()
    }

    override val layoutRes = R.layout.fragment_stats

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        graphLineChart.setBackgroundColor(Color.WHITE)
        graphLineChart.description.isEnabled = false
        graphLineChart.setTouchEnabled(false)
        graphLineChart.setDrawGridBackground(false)

        // enable scaling and dragging
        graphLineChart.isDragEnabled = true
        graphLineChart.setScaleEnabled(true)
        graphLineChart.setPinchZoom(false)

        val xAxis = graphLineChart.xAxis

        val yAxis = graphLineChart.axisLeft
        graphLineChart.axisRight.isEnabled = false
        yAxis.axisMaximum = 100f
        yAxis.axisMinimum = 0f

        // draw limit lines behind data instead of on top
        yAxis.setDrawLimitLinesBehindData(false)
        xAxis.setDrawLimitLinesBehindData(false)

        setData(30, 100f)

        val l: Legend = graphLineChart.legend
        l.form = LegendForm.DEFAULT

        tvDateRange.onClick { openDateMenu() }
    }

    private fun setData(count: Int, range: Float) {
        val values = ArrayList<Entry>()
            values.add(Entry(0f, 6.2f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(1f, 15f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(2f, 24f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(3f, 21.2f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(4f, 39f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(5f, 45.2f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(6f, 0f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(7f, 51.4f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(8f, 60f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(9f, 40f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(10f, 15f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(11f, 15f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(12f, 6.2f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(13f, 15f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(14f, 24f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(15f, 21.2f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(16f, 39f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(17f, 45.2f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(18f, 0f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(19f, 51.4f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(20f, 100f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(21f, 80f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(22f, 6.2f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(23f, 15f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(24f, 24f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(25f, 21.2f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(26f, 39f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(27f, 45.2f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(28f, 0f, resources.getDrawable(R.drawable.ic_check)))
            values.add(Entry(29f, 51.4f, resources.getDrawable(R.drawable.ic_check)))
        var set1: LineDataSet
        graphLineChart.data?.let {
            if (it.dataSetCount > 0) {
                set1 = graphLineChart.data.getDataSetByIndex(0) as LineDataSet
                set1.values = values
                set1.notifyDataSetChanged()
                graphLineChart.data.notifyDataChanged()
                graphLineChart.notifyDataSetChanged()
            }
        } ?: kotlin.run {
            set1 = LineDataSet(values, "DataSet 1").apply {
                setDrawIcons(false)
                color = Color.BLACK
                setCircleColor(Color.BLACK)
                lineWidth = 1f
                circleRadius = 3f
                setDrawCircleHole(false)
                formLineWidth = 1f
                formSize = 15f
                valueTextSize = 7f
                setDrawFilled(false)
                fillFormatter = IFillFormatter { dataSet, dataProvider ->
                    graphLineChart.axisLeft.axisMinimum
                }
            }

            val dataSets = java.util.ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the data sets
            val data = LineData(dataSets)
            graphLineChart.data = data
        }
    }

    private fun openDateMenu() {
        val popup = PopupMenu(requireContext(), tvDateRange)
        popup.inflate(R.menu.date_range_menu)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menuDays -> setStatRange("day")
                R.id.menuWeeks -> setStatRange("week")
                R.id.menuMonths -> setStatRange("month")
            }
            return@setOnMenuItemClickListener true
        }
        popup.show()
    }

    private fun setStatRange(value: String) {
        when(value) {
            "day" -> {}
            "week" -> {}
            "month" -> {}
        }
    }

}