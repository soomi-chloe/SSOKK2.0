package com.example.ssokk20ex.ui.alarm

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ssokk20ex.R
import kotlinx.android.synthetic.main.activity_add_blood_sugar_notice.*


class AddBloodSugarNotice : AppCompatActivity() {

    companion object {
        var isChecked_before: Boolean = true
        var isChecked_after: Boolean = true
    }

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_blood_sugar_notice)
        supportActionBar?.hide()

        updateString()
        OnClickTime()

        btn_addNotice_beforeMeal.setOnClickListener {
            if(isChecked_after) {
                btn_addNotice_beforeMeal.setImageResource(R.drawable.before_meal_clicked)
                btn_addNotice_afterMeal.setImageResource(R.drawable.after_meal)

                AddBloodSugarNotice.isChecked_before = true
                AddBloodSugarNotice.isChecked_after = false
            }
        }

        btn_addNotice_afterMeal.setOnClickListener {
            if(isChecked_before) {
                btn_addNotice_beforeMeal.setImageResource(R.drawable.before_meal)
                btn_addNotice_afterMeal.setImageResource(R.drawable.after_meal_clicked)

                AddBloodSugarNotice.isChecked_before = false
                AddBloodSugarNotice.isChecked_after = true
            }
        }

        //< 아이콘 이전 화면
        btn_img_before_present_bun.setOnClickListener {
            val presentAlarmN = findViewById<TextView>(R.id.txt_aDayNum)
            var intPresentAlarmN: Int = Integer.valueOf(presentAlarmN.getText().toString())
            if (intPresentAlarmN == 1){
                Toast.makeText(this, "첫 알람입니다", Toast.LENGTH_LONG).show()
            } else {
                intPresentAlarmN--
                Toast.makeText(this, intPresentAlarmN.toString(), Toast.LENGTH_LONG).show()
                txt_aDayNum.setText(intPresentAlarmN.toString())
            }
        }

        //'이전' 텍스트 이전 화면
        txt_before_present_bun.setOnClickListener {
            val presentAlarmN = findViewById<TextView>(R.id.txt_aDayNum)
            var intPresentAlarmN: Int = Integer.valueOf(presentAlarmN.getText().toString())
            if (intPresentAlarmN == 1){
                Toast.makeText(this, "첫 알람입니다", Toast.LENGTH_LONG).show()
            } else {
                intPresentAlarmN--
                Toast.makeText(this, intPresentAlarmN.toString(), Toast.LENGTH_LONG).show()
                txt_aDayNum.setText(intPresentAlarmN.toString())
            }
        }

        //'다음' 텍스트 다음 화면
        txt_after_present_bun.setOnClickListener {
            val AlarmN = findViewById<EditText>(R.id.txt_aDayN)
            val presentAlarmN = findViewById<TextView>(R.id.txt_aDayNum)
            var intAlarmN: Int = Integer.valueOf(AlarmN.getText().toString())
            var intPresentAlarmN: Int = Integer.valueOf(presentAlarmN.getText().toString())

            if (intPresentAlarmN == intAlarmN){
                Toast.makeText(this, "마지막 알람입니다", Toast.LENGTH_LONG).show()
            } else {
                intPresentAlarmN++
                Toast.makeText(this, intPresentAlarmN.toString(), Toast.LENGTH_LONG).show()
                txt_aDayNum.setText(intPresentAlarmN.toString())
            }
        }

        btn_cancel.setOnClickListener {
            startActivity(Intent(this, AlarmFunctions::class.java))
        }

        btn_save.setOnClickListener {
            startActivity(Intent(this, AlarmFunctions::class.java))
            Toast.makeText(this, "알람이 저장되었습니다", Toast.LENGTH_LONG).show()
        }

    }

    //총 알람수 없데이트하기
    private  fun updateString() {
        val AlarmN = findViewById<TextView>(R.id.txt_aDayN)
        txt_bunN.setText(AlarmN.getText())
    }

    private fun OnClickTime() {
        val textView = findViewById<TextView>(R.id.textView)
        val timePicker = findViewById<TimePicker>(R.id.timePicker)
        timePicker.setOnTimeChangedListener { _, hour, minute -> var hour = hour
            var am_pm = ""
            // AM_PM decider logic
            when {hour == 0 -> { hour += 12
                am_pm = "AM"
            }
                hour == 12 -> am_pm = "PM"
                hour > 12 -> { hour -= 12
                    am_pm = "PM"
                }
                else -> am_pm = "AM"
            }
            if (textView != null) {
                val hour = if (hour < 10) "0" + hour else hour
                val min = if (minute < 10) "0" + minute else minute
                // display format of time
                val msg = "Time is: $hour : $min $am_pm"
                textView.text = msg
                textView.visibility = ViewGroup.VISIBLE
            }
        }
    }

}