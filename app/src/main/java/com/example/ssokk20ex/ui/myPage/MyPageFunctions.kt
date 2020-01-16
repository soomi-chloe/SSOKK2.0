package com.example.ssokk20ex.ui.myPage

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ssokk20ex.MainActivity
import com.example.ssokk20ex.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_mypage.*

class MyPageFunctions : AppCompatActivity() {
    var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_mypage)

        auth = FirebaseAuth.getInstance()

        signOutBtn.setOnClickListener {
            Toast.makeText(this, "로그아웃 중입니다", Toast.LENGTH_LONG).show()
            //signOut()
        }
    }

    private fun signOut() {
        auth?.signOut()
        Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_LONG).show()
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}