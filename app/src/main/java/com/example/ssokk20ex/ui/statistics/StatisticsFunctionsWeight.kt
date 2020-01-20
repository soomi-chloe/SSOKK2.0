package com.example.ssokk20ex.ui.statistics

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.ssokk20ex.MyPage
import com.example.ssokk20ex.R
import com.example.ssokk20ex.ui.record.RecordWeightDTO
import com.example.ssokk20ex.ui.record.UserDTO
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.weight_record_weekly.*
import kotlinx.android.synthetic.main.weight_record_weekly.allRecordBtn
import kotlinx.android.synthetic.main.weight_record_weekly.bloodSugarStatisticsBtn

class StatisticsFunctionsWeight : AppCompatActivity() {
    private val entries = ArrayList<Entry>()
    companion object {
        var isChecked_monthly_weight: Boolean?=null
        var isChecked_weekly_weight: Boolean?=null
        var weightList = ArrayList<RecordWeightDTO>()
        var infoList = ArrayList<UserDTO>()
    }
    val stat = StatisticsFunctions()
    var chart :LineChart? = null

    var xAxisValues: List<String> = java.util.ArrayList(
        listOf(
            "월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일"
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weight_record_weekly)
        supportActionBar?.hide()

        weeklyBtn_bmi.setImageResource(R.drawable.weekly_clicked)
        monthlyBtn_bmi.setImageResource(R.drawable.monthly)

        chart = findViewById<LineChart>(R.id.bmiGraph)

        drawBmiChart()

        var setting = findViewById<ImageButton>(R.id.setting)
        setting.setOnClickListener {
            startActivity(Intent(this, MyPage::class.java))
        }

        monthlyBtn_bmi.setOnClickListener {
            isChecked_weekly_weight = false
            isChecked_monthly_weight = true
            startActivity(Intent(this, StatisticsFunctionsWeight_monthly::class.java))
        }

        bloodSugarStatisticsBtn.setOnClickListener {
            startActivity(Intent(this, StatisticsFunctions::class.java))
        }

        allRecordBtn.setOnClickListener {
            startActivity(Intent(this,StatisticsFunctions_AllRecord::class.java))
        }
    }

    private fun drawBmiChart() {
        entries.add(Entry(0f, getBMI("2020-1-18")))
        entries.add(Entry(1f, getBMI("2020-1-19")))
        entries.add(Entry(2f, getBMI("2020-1-20")))
        entries.add(Entry(3f, 0f))
        entries.add(Entry(4f, 0f))
        entries.add(Entry(5f, 0f))
        entries.add(Entry(6f, 0f))

        val dataSet = LineDataSet(entries, "BMI")
        dataSet.lineWidth = 2f
        dataSet.circleRadius = 3f
        dataSet.setCircleColor(Color.parseColor("#f16161"))
        dataSet.color = Color.parseColor("#f16161")
        dataSet.setDrawCircleHole(false)
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

    public fun getBMI(date: String) : Float {
        var bmi = 0f
        var height = 0f
        for(i in 0..weightList.size-1) {
            if(weightList.get(i).recordDate == date) {
                bmi = weightList.get(i).recordWeight.toString().toFloat()
                height = infoList.get(0).height.toString().toFloat()
                break
            }
        }
        bmi = bmi / (height/100 * height/100)
        return bmi
    }
}