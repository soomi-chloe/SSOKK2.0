package com.example.ssokk20ex

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ssokk20ex.ui.statistics.StatisticsFunctionsWeight
import com.example.ssokk20ex.ui.statistics.StatisticsFunctionsWeight.Companion.infoList
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_mypage.*

class MyPage : AppCompatActivity(){
    var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)
        supportActionBar?.hide()

        userNameTxt.text = infoList.get(0).name
        userContactTxt.text = infoList.get(0).email

        auth = FirebaseAuth.getInstance()

        home_mypage.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        var setting = findViewById<ImageButton>(R.id.setting)
        setting.setOnClickListener {
            startActivity(Intent(this, MyPage::class.java))
        }

        signOutBtn.setOnClickListener {
            signOut()
        }

        withdrawalBtn.setOnClickListener {
            deleteUser()
        }

        signOutBtn2.setOnClickListener {
            signOut()
        }

        withdrawalBtn2.setOnClickListener {
            deleteUser()
        }
    }

    private fun signOut() {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setTitle("로그아웃")
        builder.setMessage("정말 로그아웃 하시겠습니까?")
        builder.setPositiveButton("네") {dialog, which ->
            auth?.signOut()
            finish()
            Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_LONG).show()

            val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
            val editor = pref.edit()

            editor.putBoolean("isSignedIn", false)
            editor.apply()

            var intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        builder.setNegativeButton("아니오", null)

        var alertDialog = builder.create()
        alertDialog.show()
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setTitle("회원탈퇴")
        builder.setMessage("정말 탈퇴 하시겠습니까? 탈퇴한 이후로는 앱 사용에 제한이 있으며 저장된 기록이 모두 삭제됩니다.")
        builder.setPositiveButton("네") {dialog, which ->
            auth?.getCurrentUser()?.delete()
                ?.addOnCompleteListener { task ->
                    if(task.isSuccessful()) {
                        Toast.makeText(this, "탈퇴 되었습니다", Toast.LENGTH_LONG).show()

                        val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
                        val editor = pref.edit()

                        editor.putBoolean("isSignedIn", false)
                        editor.putBoolean("isNew", false)
                        editor.apply()

                        var intent = Intent(this, SignInActivity::class.java)
                        startActivity(intent)
                    } else Toast.makeText(this, "회원탈퇴 도중 문제가 생겼습니다", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("아니오", null)

        var alertDialog = builder.create()
        alertDialog.show()
    }
}