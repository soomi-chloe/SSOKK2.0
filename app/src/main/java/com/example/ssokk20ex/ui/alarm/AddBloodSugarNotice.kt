package com.example.ssokk20ex.ui.alarm

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


class AddBloodSugarNotice : AppCompatActivity() {

    companion object {
        var isChecked_before: Boolean = true
        var isChecked_after: Boolean = true
    }

    private var firestore : FirebaseFirestore? = null

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
                //현재 알람 저장하고
                addDatabase()


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
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun OnClickTime() {
        val textView = findViewById<TextView>(R.id.textView)
        val timePicker = findViewById<TimePicker>(R.id.timePicker)
        timePicker.setOnTimeChangedListener(TimePicker.OnTimeChangedListener { timePicker, hour, minute ->
            var hour = hour
            textView.text = hour.toString() + minute.toString()
        })
        }

    private fun addDatabase() {
//        if (edtAddBreed.text.isEmpty() || edtAddGender.text.isEmpty() ||
//            edtAddAge.text.isEmpty() || edtAddPhoto.text.isEmpty()) {
//            txtAddResult.text = "입력되지 않은 값이 있습니다."
//            return
//        }
        var meal : String ?= null
        if(isChecked_before == true){
            meal = "before"
            } else if(isChecked_after==true)
        {
            meal = "after"
        }

        //val timePicker = findViewById<TimePicker>(R.id.timePicker)

        var bloodSugarDTO : BloodSugarDTO?= null

//        timePicker.setOnTimeChangedListener(TimePicker.OnTimeChangedListener { timePicker, hour, minute ->
//            //hourC = hour
//            //textView.text = "Hour: "+ hour + " Minute : "+ minute
//            textView.text = hour.toString() + minute.toString()
//
//        })
       // if(textView.text.toString() == "TextView")


        bloodSugarDTO = BloodSugarDTO(
            txt_bunN.text.toString(),
            txt_aDayNum.text.toString(),
            meal,
            textView.text.toString()
        )

        //val document = edtAddBreed.text.toString()
        val document = txt_aDayNum.text.toString()

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
    }

    }
