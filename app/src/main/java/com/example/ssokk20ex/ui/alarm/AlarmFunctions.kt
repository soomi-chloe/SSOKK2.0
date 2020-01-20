package com.example.ssokk20ex.ui.alarm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.ssokk20ex.MyPage
import com.example.ssokk20ex.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_blood_sugar_notice.*
import kotlinx.android.synthetic.main.activity_blood_sugar_notice.*

class AlarmFunctions : AppCompatActivity() {

//    private var adapter: BloodSugarAdapter? = null
    private var firestore : FirebaseFirestore? = null
    var alarmTotalN :String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_sugar_notice)
        supportActionBar?.hide()

        // 알람수를 가져온다
        updateUI("user1")

        var setting = findViewById<ImageButton>(R.id.setting)
        setting.setOnClickListener {
            startActivity(Intent(this, MyPage::class.java))
        }

        tab_blood_sugar.setOnClickListener {
            startActivity(Intent(this, AlarmFunctions::class.java))
        }

        tab_pill.setOnClickListener {
            startActivity(Intent(this, MedicineNotice::class.java))
        }

        btn_fixBloodSugarNotice.setOnClickListener {
            startActivity(Intent(this, AddBloodSugarNotice::class.java))
        }

    }

    private fun updateUI(key: String) {
//        progressBarView.visibility = View.VISIBLE
        firestore = FirebaseFirestore.getInstance()
        firestore?.collection("bloodSugarAlarm")?.document(key)?.get()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var bloodSugarDTO = task.result?.toObject(BloodSugarDTO::class.java)
                    alarmTotalN = bloodSugarDTO?.alarmTotalN
                    runOnUiThread() {
                        txt_nextNotice_pillName3.text = alarmTotalN

                    }
                }
                else {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
    }
}
