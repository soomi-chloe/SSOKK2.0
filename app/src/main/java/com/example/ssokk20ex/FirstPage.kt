package com.example.ssokk20ex

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class FirstPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_page)
        supportActionBar?.hide()

        //Thread.sleep(10000)

        startActivity(Intent(this, IntroActivity::class.java))
    }
}
