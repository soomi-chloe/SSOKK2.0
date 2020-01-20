package com.example.ssokk20ex

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity


class FirstPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_page)
        supportActionBar?.hide()

        Handler().postDelayed(Runnable {
            startActivity(Intent(this, IntroActivity::class.java))
            this.finish()
        }, 3000)
    }
}
