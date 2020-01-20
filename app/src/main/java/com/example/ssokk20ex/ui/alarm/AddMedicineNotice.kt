package com.example.ssokk20ex.ui.alarm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.example.ssokk20ex.MyPage
import com.example.ssokk20ex.R
import kotlinx.android.synthetic.main.activity_add_blood_sugar_notice.*

class AddMedicineNotice : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medicine_notice)
        supportActionBar?.hide()

        var setting = findViewById<ImageButton>(R.id.setting)
        setting.setOnClickListener {
            startActivity(Intent(this, MyPage::class.java))
        }

    }

}

