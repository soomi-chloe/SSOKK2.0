package com.example.ssokk20ex.ui.statistics

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ssokk20ex.R
import kotlinx.android.synthetic.main.all_records.*


class StatisticsFunctions_AllRecord : AppCompatActivity(), SensorEventListener {
    var sensorManager: SensorManager? = null
    var running: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_records)
        supportActionBar?.hide()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager?

        bloodSugarStatisticsBtn_allRecord.setOnClickListener {
            startActivity(Intent(this, StatisticsFunctions::class.java))
        }

        bmiStatisticsBtn_allRecord.setOnClickListener {
            startActivity(Intent(this, StatisticsFunctionsWeight::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        running = true
        var stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        //if(stepSensor != null)
            //sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        sensorManager?.registerListener(this, sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
        //else Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
    }

//    override fun onPause() {
//        super.onPause()
//        running = false
//        sensorManager?.unregisterListener(this)
//    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onSensorChanged(event: SensorEvent) {
        if(running)
            stepsTxt.setText(""+event.values[0]+" 걸음")
    }
}