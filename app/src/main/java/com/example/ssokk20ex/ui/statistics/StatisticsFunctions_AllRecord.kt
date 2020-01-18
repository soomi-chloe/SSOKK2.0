package com.example.ssokk20ex.ui.statistics

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ssokk20ex.R
import kotlinx.android.synthetic.main.all_records.*
import kotlinx.android.synthetic.main.all_records.bmiStatisticsBtn_allRecord

class StatisticsFunctions_AllRecord : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_records)
        supportActionBar?.hide()

        bloodSugarStatisticsBtn_allRecord.setOnClickListener {
            startActivity(Intent(this, StatisticsFunctions::class.java))
        }

        bmiStatisticsBtn_allRecord.setOnClickListener {
            startActivity(Intent(this, StatisticsFunctionsWeight::class.java))
        }
    }
}