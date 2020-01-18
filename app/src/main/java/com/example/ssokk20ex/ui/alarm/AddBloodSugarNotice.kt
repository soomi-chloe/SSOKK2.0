package com.example.ssokk20ex.ui.alarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ssokk20ex.R

class AddBloodSugarNotice : AppCompatActivity() {

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_blood_sugar_notice)
        supportActionBar?.hide()
    }
}