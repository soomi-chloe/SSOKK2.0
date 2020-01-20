package com.example.ssokk20ex.ui.alarm

import AlarmFunctions
import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.ssokk20ex.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_blood_sugar_notice.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class AddBloodSugarNotice : AppCompatActivity() {

    companion object {
        var isChecked_before: Boolean = true
        var isChecked_after: Boolean = true
    }

    private var firestore : FirebaseFirestore? = null

    var alarm_1:String? = null
    var alarm_1_MealBeforeOrAfter:String? = null
    var alarm_1_Time:String? = null

    var alarm_2:String? = null
    var alarm_2_MealBeforeOrAfter:String? = null
    var alarm_2_Time:String? = null

    var alarm_3:String? = null
    var alarm_3_MealBeforeOrAfter:String? = null
    var alarm_3_Time:String? = null

    var alarm_4:String? = null
    var alarm_4_MealBeforeOrAfter:String? = null
    var alarm_4_Time:String? = null

    var alarm_5:String? = null
    var alarm_5_MealBeforeOrAfter:String? = null
    var alarm_5_Time:String? = null

    var alarm_6:String? = null
    var alarm_6_MealBeforeOrAfter:String? = null
    var alarm_6_Time:String? = null

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_blood_sugar_notice)
        supportActionBar?.hide()

        OnClickTime()

        txt_aDayN.addTextChangedListener(object: TextWatcher{
            //입력이 끝났을 때
            override fun afterTextChanged(s: Editable?) {
                txt_bunN.setText(findViewById<TextView>(R.id.txt_aDayN).getText().toString())
                Toast.makeText(applicationContext, "가능한 알람의 최대 갯수는 6개까지입니다.", Toast.LENGTH_LONG).show()

            }

            //입력하기 전에
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(txt_bunN !=txt_aDayN) {
                    txt_bunN.setText(findViewById<TextView>(R.id.txt_aDayN).getText().toString())
                }
            }

            //입력되는 텍스트에 변화가 있을 때
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(txt_bunN !=txt_aDayN) {
                    txt_bunN.setText(findViewById<TextView>(R.id.txt_aDayN).getText().toString())
                }
        }
        })

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

        btn_before_present_bun.setOnClickListener {
            val presentAlarmN = findViewById<TextView>(R.id.txt_aDayNum)
            var intPresentAlarmN: Int = Integer.valueOf(presentAlarmN.getText().toString())

            btn_after_present_bun.setImageResource(R.drawable.right_page_active)

            //현재 알람 저장하고
            modifyData()

            if (intPresentAlarmN == 1){
                Toast.makeText(this, "첫 알람입니다", Toast.LENGTH_LONG).show()
            } else {
                if (intPresentAlarmN == 2)
                    btn_before_present_bun.setImageResource(R.drawable.left_page)

                intPresentAlarmN--
                Toast.makeText(this, intPresentAlarmN.toString(), Toast.LENGTH_LONG).show()
                txt_aDayNum.setText(intPresentAlarmN.toString())
            }
        }

        btn_after_present_bun.setOnClickListener {
            val AlarmN = findViewById<EditText>(R.id.txt_aDayN)
            val presentAlarmN = findViewById<TextView>(R.id.txt_aDayNum)
            var intAlarmN: Int = Integer.valueOf(AlarmN.getText().toString())
            var intPresentAlarmN: Int = Integer.valueOf(presentAlarmN.getText().toString())

            btn_before_present_bun.setImageResource(R.drawable.left_page_active)

            //현재 알람 저장하고
            modifyData()

            if (intPresentAlarmN == intAlarmN){
                Toast.makeText(this, "마지막 알람입니다", Toast.LENGTH_LONG).show()
            } else {
                if (intPresentAlarmN == intAlarmN-1)
                    btn_after_present_bun.setImageResource(R.drawable.right_page)

                //다음 알람으로 가기
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
            //현재 알람 저장하고
            addDatabase()
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun OnClickTime() {
        val textView = findViewById<TextView>(R.id.textView)
        val timePicker = findViewById<TimePicker>(R.id.timePicker)
        timePicker.setOnTimeChangedListener(TimePicker.OnTimeChangedListener { timePicker, hour, minute ->
            var hour = hour
            var am_pm: String ?=null
            if(hour>12){
                hour=hour-12
                am_pm=" PM"}
            else{am_pm=" AM"}
            textView.text = hour.toString() +" : "+ minute.toString() + am_pm
        })
        }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun modifyData() {

        val presentAlarmN = findViewById<TextView>(R.id.txt_aDayNum)
        var intPresentAlarmN: Int = Integer.valueOf(presentAlarmN.getText().toString())

        if (intPresentAlarmN==1) {
            alarm_1 = "1"

            if (isChecked_before == true) {
                alarm_1_MealBeforeOrAfter = "before"
            } else if (isChecked_after == true) {
                alarm_1_MealBeforeOrAfter = "after"
            }

            alarm_1_Time=textView.getText().toString()
        }

        if (intPresentAlarmN==2) {
            alarm_2 = "2"

            if (isChecked_before == true) {
                alarm_2_MealBeforeOrAfter = "before"
            } else if (isChecked_after == true) {
                alarm_2_MealBeforeOrAfter = "after"
            }

            alarm_2_Time=textView.getText().toString()
        }

        if (intPresentAlarmN==3) {
            alarm_3 = "3"

            if (isChecked_before == true) {
                alarm_3_MealBeforeOrAfter = "before"
            } else if (isChecked_after == true) {
                alarm_3_MealBeforeOrAfter = "after"
            }

            alarm_3_Time=textView.getText().toString()
        }

        if (intPresentAlarmN==4) {
            alarm_4 = "4"

            if (isChecked_before == true) {
                alarm_4_MealBeforeOrAfter = "before"
            } else if (isChecked_after == true) {
                alarm_4_MealBeforeOrAfter = "after"
            }

            alarm_4_Time=textView.getText().toString()
        }

        if (intPresentAlarmN==5) {
            alarm_5 = "5"

            if (isChecked_before == true) {
                alarm_5_MealBeforeOrAfter = "before"
            } else if (isChecked_after == true) {
                alarm_5_MealBeforeOrAfter = "after"
            }

            alarm_5_Time=textView.getText().toString()
        }

        if (intPresentAlarmN==6) {
            alarm_6 = "6"

            if (isChecked_before == true) {
                alarm_6_MealBeforeOrAfter = "before"
            } else if (isChecked_after == true) {
                alarm_6_MealBeforeOrAfter = "after"
            }

            alarm_6_Time=textView.getText().toString()
        }


    }//add dB

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addDatabase() {

        modifyData()

        var date = LocalDateTime.now()
        var strDate = date.format(DateTimeFormatter.ofPattern("yy-MM-dd H:mm"))

        var bloodSugarDTO = BloodSugarDTO(
            "user1",
            txt_bunN.text.toString(),

            alarm_1,
            alarm_1_MealBeforeOrAfter,
            alarm_1_Time,

            alarm_2,
            alarm_2_MealBeforeOrAfter,
            alarm_2_Time,

            alarm_3,
            alarm_3_MealBeforeOrAfter,
            alarm_3_Time,

            alarm_4,
            alarm_4_MealBeforeOrAfter,
            alarm_4_Time,

            alarm_5,
            alarm_5_MealBeforeOrAfter,
            alarm_5_Time,

            alarm_6,
            alarm_6_MealBeforeOrAfter,
            alarm_6_Time
        )

        val document = "user1"

        //progressBarAdd.visibility = View.VISIBLE
        firestore = FirebaseFirestore.getInstance()
        firestore?.collection("bloodSugarAlarm")?.document(document)
            ?.set(bloodSugarDTO)?.addOnCompleteListener {
                    task ->
                //progressBarAdd.visibility = View.GONE
                if (task.isSuccessful) {
                    Toast.makeText(this, "Firebase Success", Toast.LENGTH_LONG).show()
                    //txtAddResult.text = "Insert Success"
                } else {
                    Toast.makeText(this, "Firebase Failed", Toast.LENGTH_LONG).show()
                    //txtAddResult.text = task.exception?.message
                }
            }
    }//add dB

    }
