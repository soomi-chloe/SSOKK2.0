package com.example.ssokk20ex.ui.alarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ssokk20ex.R

class AlarmFunctions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_sugar_notice)
        supportActionBar?.hide()
    }
}
