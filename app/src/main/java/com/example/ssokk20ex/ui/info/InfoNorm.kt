package com.example.ssokk20ex.ui.info

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.ssokk20ex.MyPage
import com.example.ssokk20ex.R
import kotlinx.android.synthetic.main.activity_info_norm.*

class InfoNorm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_norm)
        supportActionBar?.hide()

        var setting = findViewById<ImageButton>(R.id.setting)
        setting.setOnClickListener {
            startActivity(Intent(this, MyPage::class.java))
        }

        btn_news_inKnow.setOnClickListener {
            startActivity(Intent(this, InfoNews::class.java))
        }

        btn_know_1.setOnClickListener {
            startActivity(Intent(this, InfoNorm1::class.java))
        }

        btn_know_2.setOnClickListener {
            startActivity(Intent(this, InfoNorm2::class.java))
        }

        btn_know_3.setOnClickListener {
            startActivity(Intent(this, InfoNorm3::class.java))
        }

        btn_know_4.setOnClickListener {
            startActivity(Intent(this, InfoNorm4::class.java))
        }

        btn_know_5.setOnClickListener {
            startActivity(Intent(this, InfoNorm5::class.java))
        }

        btn_know_6.setOnClickListener {
            startActivity(Intent(this, InfoNorm6::class.java))
        }

        btn_know_7.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://bioinformaticsandme.tistory.com/108")))
        }

        btn_know_8.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://danggun.org/page/4/?s=%EB%8B%B9%EB%87%A8%EC%83%81%EC%8B%9D")))
        }
    }
}
