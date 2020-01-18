package com.example.ssokk20ex.ui.myPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ssokk20ex.R

class MedicineNotice : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_notice)
        supportActionBar?.hide()
    }
}
