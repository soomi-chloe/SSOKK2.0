package com.example.ssokk20ex

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ssokk20ex.ui.record.UserDTO
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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

        goSignInBtn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    private fun createAccount() {
        if(userNameTxt.text.toString().isNullOrEmpty() || emailTxt.text.toString().isNullOrEmpty() || pwTxt.text.toString().isNullOrEmpty() || pwCheckTxt.text.toString().isNullOrEmpty()) {
            Toast.makeText(this, "모든 항목을 입력해주세요", Toast.LENGTH_LONG).show()
            return
        }
        if(pwTxt.text.toString() == pwCheckTxt.text.toString()) {
            val firestore = FirebaseFirestore.getInstance()
            auth?.createUserWithEmailAndPassword(emailTxt.text.toString().trim(), pwTxt.text.toString().trim())
                ?.addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        val intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }
            val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
            val UserDTO = UserDTO(userNameTxt.text.toString(),
                emailTxt.text.toString(),
                pref.getString("userGender", null),
                pref.getString("userAge", null),
                pref.getString("userHeight", null),
                pref.getString("userWeight", null))

            firestore?.collection("user_info").document(userNameTxt.text.toString())
                .set(UserDTO)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Toast.makeText(this, "저장되었습니다", Toast.LENGTH_LONG).show()
                    } else {
                    Toast.makeText(this, "오류가 발생했습니다", Toast.LENGTH_LONG).show()
                    }
                }
        } else {
            Toast.makeText(this, "비밀번호가 틀립니다", Toast.LENGTH_LONG).show()
        }

    }
}
