package com.example.ssokk20ex

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    var auth: FirebaseAuth? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        signInBtn.setOnClickListener {
            emailLogin()
        }

        createAccountBtn.setOnClickListener {
            val intent = Intent(this, UserInfo::class.java)
            startActivityForResult(intent, 0)
        }
    }

    private fun emailLogin() {
        if(signInTxt.text.toString().isNullOrEmpty() || pwTxt.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "이메일과 비밀번호를 입력해주세요", Toast.LENGTH_LONG).show()
            return
        }

        auth?.signInWithEmailAndPassword(emailTxt.text.toString(), pwTxt.text.toString())
            ?.addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
                    val editor = pref.edit()
                    editor.putBoolean("isSignedIn", true)
                    editor.apply()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "정보가 틀립니다. 다시 입력해주세요", Toast.LENGTH_LONG).show()
                }
            }
    }
}
