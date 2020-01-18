package com.example.ssokk20ex.ui.alarm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ssokk20ex.R
import com.example.ssokk20ex.ui.statistics.StatisticsFunctionsWeight
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

        btn_addNotice_afterMeal.setOnClickListener {
            if(isChecked_before) {
                btn_addNotice_beforeMeal.setImageResource(R.drawable.before_meal)
                btn_addNotice_afterMeal.setImageResource(R.drawable.after_meal_clicked)

                AddBloodSugarNotice.isChecked_before = false
                AddBloodSugarNotice.isChecked_after = true
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
}