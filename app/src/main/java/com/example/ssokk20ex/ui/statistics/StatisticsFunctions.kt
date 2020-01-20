package com.example.ssokk20ex.ui.statistics

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ssokk20ex.R
import com.example.ssokk20ex.ui.record.RecordBloodSugarDTO
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.blood_sugar_record_weekly.*

class StatisticsFunctions : AppCompatActivity(){
    private val entries = ArrayList<Entry>()
    //val bsList : MutableList<RecordBloodSugarDTO> = ArrayList()

    companion object {
        var isChecked_monthly: Boolean = true
        var isChecked_weekly: Boolean = true
        val bsList : MutableList<RecordBloodSugarDTO> = ArrayList()
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
        entries.add(Entry(0f, getAvg("2020-01-19")))
        entries.add(Entry(1f, 0f))
        entries.add(Entry(2f, 0f))
        entries.add(Entry(3f, 0f))
        entries.add(Entry(4f, 0f))
        entries.add(Entry(5f, 0f))
        entries.add(Entry(6f, 0f))
        Log.d("test", bsList.get(0).recordDate.toString())

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

    fun getBloodSugarRecord() {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("record_bloodSugar").get()
            .addOnCompleteListener{ task ->
                if(task.isSuccessful) {
                    for(dc in task.result!!.documents) {
                        bsList.add(dc.toObject(RecordBloodSugarDTO::class.java)!!)
                    }
                    Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                }
                else
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
    }

    public fun getAvg(date : String) : Float {
        var avg: Float = 0f
        var count : Int = 0

        for(i in 0..bsList.size-1) {
            if(bsList.get(i).recordDate == date) {
                avg += bsList.get(i).bloodSugar.toString().toFloat()
                count++
            }
        }
        avg /= count
        return avg
    }

}