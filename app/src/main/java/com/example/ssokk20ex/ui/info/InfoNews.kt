package com.example.ssokk20ex.ui.info

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ssokk20ex.R
import kotlinx.android.synthetic.main.activity_info_news.*

class InfoNews : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_news)
        supportActionBar?.hide()

        btn_know_inNews.setOnClickListener {
            startActivity(Intent(this, InfoNorm::class.java))
        }

        btn_article_1.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://me2.do/GCkzYLUp")))
        }

        imageButton1.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://me2.do/GCkzYLUp")))
        }

        btn_article_2.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.yna.co.kr/view/AKR20180201045300057?input=1195m")))
        }

        imageButton2.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.yna.co.kr/view/AKR20180201045300057?input=1195m")))
        }

        btn_article_3.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://news.heraldcorp.com/view.php?ud=20180904000203")))
        }

        imageButton3.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://news.heraldcorp.com/view.php?ud=20180904000203")))
        }

        btn_article_4.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://kormedi.com/1261420/%eb%8b%b9%eb%87%a8%eb%b3%91-%eb%a8%b9%ea%b1%b0%eb%a6%ac-%ec%a2%8b%ec%9d%8c-5-vs-%eb%82%98%ec%81%a8-6/")))
        }

        imageButton4.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://kormedi.com/1261420/%eb%8b%b9%eb%87%a8%eb%b3%91-%eb%a8%b9%ea%b1%b0%eb%a6%ac-%ec%a2%8b%ec%9d%8c-5-vs-%eb%82%98%ec%81%a8-6/")))
        }
    }
}
