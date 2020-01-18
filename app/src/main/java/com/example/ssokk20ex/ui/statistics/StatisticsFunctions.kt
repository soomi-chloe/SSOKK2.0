package com.example.ssokk20ex.ui.statistics

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ssokk20ex.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.blood_sugar_record_weekly.*

class StatisticsFunctions : AppCompatActivity() {
    private val entries = ArrayList<Entry>()

    companion object {
        var isChecked_monthly: Boolean = true
        var isChecked_weekly: Boolean = true
    }

    var chartBS : LineChart? = null

    var xAxisValues: List<String> = java.util.ArrayList(
        listOf(
            "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.blood_sugar_record_weekly)
        supportActionBar?.hide()

        weeklyBtn.setImageResource(R.drawable.weekly_clicked)
        monthlyBtn.setImageResource(R.drawable.monthly)

        chartBS = findViewById<LineChart>(R.id.bsGraph)

        drawBmiChartBS()

        monthlyBtn.setOnClickListener {
            isChecked_weekly = false
            isChecked_monthly = true
            startActivity(Intent(this, StatisticsFunctions_monthly::class.java))
        }

        bmiStatisticsBtn.setOnClickListener {
            startActivity(Intent(this, StatisticsFunctionsWeight::class.java))
        }

        allRecordBtn.setOnClickListener {
            startActivity(Intent(this, StatisticsFunctions_AllRecord::class.java))
        }
    }

    private fun drawBmiChartBS() {
        entries.add(Entry(0f, 39f))
        entries.add(Entry(1f, 38f))
        entries.add(Entry(2f, 33f))

        val dataSet = LineDataSet(entries, "BMI")
        dataSet.lineWidth = 2f
        dataSet.circleRadius = 3f
        dataSet.setCircleColor(Color.parseColor("#FFA1B4DC"))
        dataSet.color = Color.parseColor("#FFA1B4DC")
        val lineData = LineData(dataSet)
        chartBS!!.data = lineData

        val xAxis: XAxis = chartBS!!.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)
        xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)

        val yAxisLeft: YAxis = chartBS!!.axisLeft
        yAxisLeft.granularity = 1f
        yAxisLeft.setDrawGridLines(false)

        val yAxisRight: YAxis = chartBS!!.axisRight
        yAxisRight.isEnabled = false

        chartBS!!.legend.isEnabled = false
        chartBS!!.description.isEnabled = false

        chartBS!!.invalidate()
    }
}