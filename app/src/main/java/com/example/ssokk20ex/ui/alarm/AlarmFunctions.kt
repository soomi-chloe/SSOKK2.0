package com.example.ssokk20ex.ui.alarm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ssokk20ex.R
import kotlinx.android.synthetic.main.activity_blood_sugar_notice.*

class AlarmFunctions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_sugar_notice)
        supportActionBar?.hide()

        tab_blood_sugar.setOnClickListener {
            startActivity(Intent(this, AlarmFunctions::class.java))
        }

        tab_pill.setOnClickListener {
            startActivity(Intent(this, MedicineNotice::class.java))
        }
    }
}
