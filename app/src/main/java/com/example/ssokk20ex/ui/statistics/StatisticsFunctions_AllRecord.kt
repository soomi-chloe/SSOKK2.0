package com.example.ssokk20ex.ui.statistics

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.ssokk20ex.MyPage
import com.example.ssokk20ex.R
import com.example.ssokk20ex.ui.record.RecordBloodSugarDTO
import com.example.ssokk20ex.ui.record.RecordWeightDTO
import com.example.ssokk20ex.ui.statistics.StatisticsFunctions.Companion.bsList
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.all_records.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class StatisticsFunctions_AllRecord : AppCompatActivity(), SensorEventListener {
    var sensorManager: SensorManager? = null
    var running: Boolean = false
    var weightList = ArrayList<RecordWeightDTO>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_records)
        supportActionBar?.hide()

        var bsStatArray = arrayOf(txt_valueOfBS1, txt_valueOfBS2, txt_valueOfBS3, txt_valueOfBS4, txt_valueOfBS5, txt_valueOfBS6)

        var index = 0
        var date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-M-d"))

        for(i in 0 until bsList.size) {
            if(bsList.get(i).recordDate == date) {
                bsStatArray[index].text = bsList.get(index).bloodSugar
                index++
            }
        }
        if(index < 6) {
            for(i in index..5)
                bsStatArray[index].text = "-"
        }

        for(i in 0..weightList.size-1) {
            if(weightList.get(i).recordDate == date) {
                txt_weightOfDay.text = weightList.get(i).recordWeight
                break
            }
            else
                txt_weightOfDay.text = "-"
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager?

        var setting = findViewById<ImageButton>(R.id.setting)
        setting.setOnClickListener {
            startActivity(Intent(this, MyPage::class.java))
        }

        bloodSugarStatisticsBtn_allRecord.setOnClickListener {
            startActivity(Intent(this, StatisticsFunctions::class.java))
        }

        bmiStatisticsBtn_allRecord.setOnClickListener {
            startActivity(Intent(this, StatisticsFunctionsWeight::class.java))
        }

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Toast.makeText(applicationContext, ""+year + "-" + (month+1) + "-" + dayOfMonth, Toast.LENGTH_SHORT).show()
            var index = 0
            for(i in 0 until bsList.size) {
                if(bsList.get(i).recordDate == (""+year + "-" + (month+1)+ "-" + dayOfMonth)) {
                    bsStatArray[index].text = bsList.get(index).bloodSugar
                    index++
                }
            }
            if(index < 6) {
                for(i in index..5)
                    bsStatArray[index].text = "-"
            }

            for(i in 0 until weightList.size) {
                if(weightList.get(i).toString() == (""+year + "-" + (month+1)+ "-" + dayOfMonth)) {
                    txt_weightOfDay.text = weightList.get(i).recordWeight + " kg"
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        running = true
        var stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if(stepSensor != null)
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        running = false
        sensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onSensorChanged(event: SensorEvent) {
        if(running)
            stepsTxt.setText(""+event.values[0].toInt()+" 걸음")
    }
}