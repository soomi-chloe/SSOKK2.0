package com.example.ssokk20ex.ui.alarm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import com.example.ssokk20ex.MainActivity
import com.example.ssokk20ex.MyPage
import com.example.ssokk20ex.R
import kotlinx.android.synthetic.main.activity_add_blood_sugar_notice.*
import kotlinx.android.synthetic.main.activity_add_medicine_notice.*

class AddMedicineNotice : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medicine_notice)
        supportActionBar?.hide()

        home_medicine_notice.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        var setting = findViewById<ImageButton>(R.id.setting)
        setting.setOnClickListener {
            startActivity(Intent(this, MyPage::class.java))
        }

    }

}

