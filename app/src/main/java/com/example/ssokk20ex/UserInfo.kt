package com.example.ssokk20ex

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ssokk20ex.ui.statistics.StatisticsFunctions
import com.example.ssokk20ex.ui.statistics.StatisticsFunctions_monthly
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_user_info.*

class UserInfo : AppCompatActivity() {
    private var isChecked_female = true
    private var isChecked_male = false
    var gender : String ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        supportActionBar?.hide()

        femaleBtn.setOnClickListener {
            if(isChecked_male) {
                isChecked_male = false
                isChecked_female = true
                femaleBtn.setImageResource(R.drawable.female_clicked)
                maleBtn.setImageResource(R.drawable.male)
            }
            gender = "F"
        }

        maleBtn.setOnClickListener {
            if(isChecked_female) {
                isChecked_male = true
                isChecked_female = false
                femaleBtn.setImageResource(R.drawable.female)
                maleBtn.setImageResource(R.drawable.male_clicked)
            }
            gender = "M"
        }

        nextBtn.setOnClickListener {
            val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
            val editor = pref.edit()
            editor.putString("userGender", gender)
            editor.putString("userAge", ageTxt.text.toString())
            editor.putString("userHeight", heightTxt.text.toString())
            editor.putString("userWeight", weightTxt.text.toString())
            editor.apply()

            startActivity(Intent(this, SignUpActivity::class.java))
        }

        goSignInBtn2.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }
}
