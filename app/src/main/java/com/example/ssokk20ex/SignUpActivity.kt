package com.example.ssokk20ex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        signUpBtn.setOnClickListener {
            createAccount()
        }
    }

    private fun createAccount() {
        if(emailTxt.text.toString().isNullOrEmpty() || pwTxt.text.toString().isNullOrEmpty() || pwCheckTxt.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "모든 항목을 입력해주세요", Toast.LENGTH_LONG).show()
            return
        }
        if(pwTxt.text.toString() == pwCheckTxt.text.toString()) {
            auth?.createUserWithEmailAndPassword(emailTxt.text.toString().trim(), pwTxt.text.toString().trim())
                ?.addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        val intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(this, "비밀번호가 틀립니다", Toast.LENGTH_LONG).show()
        }

    }
}
