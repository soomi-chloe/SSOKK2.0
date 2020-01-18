package com.example.ssokk20ex.ui.statistics

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ssokk20ex.R
import com.example.ssokk20ex.ui.statistics.StatisticsFunctionsWeight.Companion.isChecked_monthly_weight
import com.example.ssokk20ex.ui.statistics.StatisticsFunctionsWeight.Companion.isChecked_weekly_weight
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.blood_sugar_record_weekly.allRecordBtn
import kotlinx.android.synthetic.main.blood_sugar_record_weekly.bloodSugarStatisticsBtn
import kotlinx.android.synthetic.main.weight_record_weekly.*

class StatisticsFunctionsWeight_monthly : AppCompatActivity() {

    private val entries = ArrayList<Entry>()
    var chart : LineChart? = null

    var xAxisValues: List<String> = java.util.ArrayList(
        listOf(
            "일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weight_record_weekly)
        supportActionBar?.hide()

        weeklyBtn_bmi.setImageResource(R.drawable.weekly)
        monthlyBtn_bmi.setImageResource(R.drawable.monthly_clicked)

        chart = findViewById<LineChart>(R.id.bmiGraph)

        drawBmiChart()

        weeklyBtn_bmi.setOnClickListener {
            isChecked_weekly_weight = true
            isChecked_monthly_weight = false
            startActivity(Intent(this, StatisticsFunctionsWeight::class.java))
        }

        bloodSugarStatisticsBtn.setOnClickListener {
            startActivity(Intent(this, StatisticsFunctions::class.java))
        }

        allRecordBtn.setOnClickListener {
            startActivity(Intent(this,StatisticsFunctions_AllRecord::class.java))
        }
    }

    private fun drawBmiChart() {
        entries.add(Entry(0f, 39f))
        entries.add(Entry(1f, 38f))
        entries.add(Entry(2f, 33f))

        val dataSet = LineDataSet(entries, "BMI")
        dataSet.lineWidth = 2f
        dataSet.circleRadius = 3f
        dataSet.setCircleColor(Color.parseColor("#FFA1B4DC"))
        dataSet.color = Color.parseColor("#FFA1B4DC")
        val lineData = LineData(dataSet)
        chart!!.data = lineData

        val xAxis: XAxis = chart!!.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.setDrawGridLines(false)
        xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)

        val yAxisLeft: YAxis = chart!!.axisLeft
        yAxisLeft.granularity = 1f
        yAxisLeft.setDrawGridLines(false)

        val yAxisRight: YAxis = chart!!.axisRight
        yAxisRight.isEnabled = false

        chart!!.legend.isEnabled = false
        chart!!.description.isEnabled = false

        chart!!.invalidate()
    }
}