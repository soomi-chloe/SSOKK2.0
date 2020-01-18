package com.example.ssokk20ex.ui.statistics

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ssokk20ex.R
import kotlinx.android.synthetic.main.blood_sugar_record_weekly.*

class StatisticsFunctions : AppCompatActivity() {
    var isChecked_weekly: Boolean = true
    var isChecked_monthly: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.blood_sugar_record_weekly)
        supportActionBar?.hide()

        weeklyBtn.setOnClickListener {
            if (isChecked_monthly) {
                weeklyBtn.setImageResource(R.drawable.weekly_clicked)
                monthlyBtn.setImageResource(R.drawable.monthly)
                isChecked_weekly = true
                isChecked_monthly = false
            }
        }

        monthlyBtn.setOnClickListener {
            if (isChecked_weekly) {
                weeklyBtn.setImageResource(R.drawable.weekly)
                monthlyBtn.setImageResource(R.drawable.monthly_clicked)
                isChecked_weekly = false
                isChecked_monthly = true
            }
        }

        allRecordBtn.setOnClickListener {
            startActivity(Intent(this, StatisticsFunctions_AllRecord::class.java))
        }
    }
}