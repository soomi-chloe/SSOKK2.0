package com.example.ssokk20ex.ui.info

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ssokk20ex.R
import kotlinx.android.synthetic.main.activity_info_lists_know.*

class Info_lists_know : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_lists_know)
        supportActionBar?.hide()

        btn_know_1.setOnClickListener {
            var intent = Intent(this, Info_content_know_1::class.java)
            startActivity(intent)
        }

        btn_know_2.setOnClickListener {
            var intent = Intent(this, Info_content_know_2::class.java)
            startActivity(intent)
        }

        btn_know_3.setOnClickListener {
            var intent = Intent(this, Info_content_know_3::class.java)
            startActivity(intent)
        }

        btn_know_4.setOnClickListener {
            var intent = Intent(this, Info_content_know_4::class.java)
            startActivity(intent)
        }

        btn_know_5.setOnClickListener {
            var intent = Intent(this, Info_content_know_5::class.java)
            startActivity(intent)
        }

        tab_blood_sugar_selfcheck.setOnClickListener {
            var intent = Intent(this, Info_lists_news::class.java)
            startActivity(intent)

        }
    }
}