package com.example.ssokk20ex.ui.statistics

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import com.example.ssokk20ex.MainActivity
import com.example.ssokk20ex.MyPage
import com.example.ssokk20ex.R
import com.example.ssokk20ex.ui.statistics.StatisticsFunctions.Companion.isChecked_monthly
import com.example.ssokk20ex.ui.statistics.StatisticsFunctions.Companion.isChecked_weekly
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.blood_sugar_record_weekly.*

class StatisticsFunctions_monthly : AppCompatActivity() {

    private val entries = ArrayList<Entry>()
    var chartBS : LineChart? = null
    val stat = StatisticsFunctions()

    var xAxisValues: List<String> = java.util.ArrayList(
        listOf(
            "첫째주", "둘째주", "셋째주", "넷째주"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.blood_sugar_record_weekly)
        supportActionBar?.hide()

        weeklyBtn.setImageResource(R.drawable.weekly)
        monthlyBtn.setImageResource(R.drawable.monthly_clicked)

        chartBS = findViewById<LineChart>(R.id.bsGraph)

        lowestBloodSugar_value.text = String.format("%.2f", stat.getAvg("2020-1-16")-11)
        averageBloodSugar_value.text = String.format("%.2f", stat.getAvg("2020-1-19")-11)
        highestBloodsSugar_value.text = String.format("%.2f", stat.getAvg("2020-1-18")-11)

        lowestBloodSugar_value2.text = String.format("%.2f", stat.getAvg("2020-1-16")+11)
        averageBloodSugar_value2.text = String.format("%.2f", stat.getAvg("2020-1-19")+11)
        highestBloodsSugar_value2.text = String.format("%.2f", stat.getAvg("2020-1-18")+11)

        drawBmiChartBS()

        home_statistics.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        var setting = findViewById<ImageButton>(R.id.setting)
        setting.setOnClickListener {
            startActivity(Intent(this, MyPage::class.java))
        }

        weeklyBtn.setOnClickListener {
            isChecked_weekly = true
            isChecked_monthly = false
            startActivity(Intent(this, StatisticsFunctions::class.java))
        }

        bmiStatisticsBtn.setOnClickListener {
            startActivity(Intent(this, StatisticsFunctionsWeight::class.java))
        }

        allRecordBtn.setOnClickListener {
            startActivity(Intent(this, StatisticsFunctions_AllRecord::class.java))
        }
    }

    private fun drawBmiChartBS() {
        entries.add(Entry(0f, stat.getAvg("2020-1-16")))
        entries.add(Entry(1f, stat.getAvg("2020-1-18")))
        entries.add(Entry(2f, stat.getAvg("2020-1-19")))
        entries.add(Entry(3f, stat.getAvg("2020-1-20")))

        val dataSet = LineDataSet(entries, "Blood Sugar")
        dataSet.lineWidth = 2f
        dataSet.circleRadius = 3f
        dataSet.setCircleColor(Color.parseColor("#f16161"))
        dataSet.color = Color.parseColor("#f16161")
        dataSet.setDrawCircleHole(false)
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