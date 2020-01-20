package com.example.ssokk20ex.ui.info

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ssokk20ex.R
import kotlinx.android.synthetic.main.activity_info_lists_know.*
import kotlinx.android.synthetic.main.activity_info_lists_selfcheck.*
import kotlinx.android.synthetic.main.activity_info_lists_selfcheck.tab_blood_sugar_know as tab_blood_sugar_know1

class Info_lists_news : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_lists_selfcheck)
        supportActionBar?.hide()

        btn_article_1.setOnClickListener() {
            val uri = Uri.parse("http://me2.do/GCkzYLUp")
            val intent = Intent(Intent.ACTION_VIEW)
            startActivity(intent)
        }

        btn_article_2.setOnClickListener() {
            val uri = Uri.parse("https://www.yna.co.kr/view/AKR20180201045300057?input=1195m")
            val intent = Intent(Intent.ACTION_VIEW)
            startActivity(intent)
        }

        btn_article_3.setOnClickListener() {
            val uri = Uri.parse("http://news.heraldcorp.com/view.php?ud=20180904000203")
            val intent = Intent(Intent.ACTION_VIEW)
            startActivity(intent)
        }

        btn_article_4.setOnClickListener() {
            val uri = Uri.parse("http://kormedi.com/1261420/%eb%8b%b9%eb%87%a8%eb%b3%91-%eb%a8%b9%ea%b1%b0%eb%a6%ac-%ec%a2%8b%ec%9d%8c-5-vs-%eb%82%98%ec%81%a8-6/")
            val intent = Intent(Intent.ACTION_VIEW)
            startActivity(intent)
        }

        tab_blood_sugar_know.setOnClickListener() {
            var intent = Intent(this, Info_lists_know::class.java)
            startActivity(intent)
        }
    }
}