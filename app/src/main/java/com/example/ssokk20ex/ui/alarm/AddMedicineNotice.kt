package com.example.ssokk20ex.ui.alarm

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageButton
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.ssokk20ex.MainActivity
import com.example.ssokk20ex.MyPage
import com.example.ssokk20ex.R
import com.example.ssokk20ex.ui.alarm.AddMedicineNotice.Companion.isChecked_Mafter
import com.example.ssokk20ex.ui.alarm.AddMedicineNotice.Companion.isChecked_Mbefore
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_blood_sugar_notice.*
import kotlinx.android.synthetic.main.activity_add_medicine_notice.*
import kotlinx.android.synthetic.main.blood_sugar_record_weekly.*

class AddMedicineNotice : AppCompatActivity() {

    companion object {
        var isChecked_Mbefore: Boolean = true
        var isChecked_Mafter: Boolean = true
        var isChecked_in30m: Boolean = true
    }

    private var firestore : FirebaseFirestore? = null
    private var firestore2 : FirebaseFirestore? = null
    private var firestore3 : FirebaseFirestore? = null

    //유저이름/약이름/몇번/몇알/식전/몇분이내/시간1/시간2/시간3
    var userName: String? = null
    var pillName: String? = null
    var alarmTotalN: String? = null
    var howManyInOnce: String? = null

    var MealBeforeOrAfter:String? = null
    var howMuchTimeBeforeOrAfter: String? = null

    var alarm_1_Time:String? = null
    var alarm_2_Time:String? = null
    var alarm_3_Time:String? = null

    var pillName1: String? = null
    var pillName2: String? = null
    var pillName3: String? = null
    var pillName4: String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medicine_notice)
        supportActionBar?.hide()

        home_medicine_notice.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        var setting = findViewById<ImageButton>(R.id.setting)
        setting.setOnClickListener {
            startActivity(Intent(this, MyPage::class.java))
        }

        editTxt_MaDayNum.addTextChangedListener(object: TextWatcher {
            //입력이 끝났을 때
            override fun afterTextChanged(s: Editable?) {
                Toast.makeText(applicationContext, "가능한 알람의 최대 갯수는 3개까지입니다.", Toast.LENGTH_LONG)
                    .show()
            }
            //입력하기 전에
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            //입력되는 텍스트에 변화가 있을 때
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        btn_addNotice_see.setOnClickListener {
            if(Integer.valueOf(editTxt_MaDayNum.text.toString())==1) {
                timePickerM.visibility = View.INVISIBLE
                timePickerM2.visibility = View.VISIBLE
                timePickerM3.visibility = View.INVISIBLE
            }else if(Integer.valueOf(editTxt_MaDayNum.text.toString())==2){
                timePickerM.visibility = View.VISIBLE
                timePickerM2.visibility = View.INVISIBLE
                timePickerM3.visibility = View.VISIBLE
            }else if(Integer.valueOf(editTxt_MaDayNum.text.toString())==3) {
                timePickerM.visibility = View.VISIBLE
                timePickerM2.visibility = View.VISIBLE
                timePickerM3.visibility = View.VISIBLE
            }
        }

        btn_addNotice_MbeforeMeal.setOnClickListener {
            if(AddMedicineNotice.isChecked_Mafter) {
                btn_addNotice_MbeforeMeal.setImageResource(R.drawable.before_meal_clicked)
                btn_addNotice_MafterMeal.setImageResource(R.drawable.after_meal)

                AddMedicineNotice.isChecked_Mbefore = true
                AddMedicineNotice.isChecked_Mafter = false
            }
        }

        btn_addNotice_MafterMeal.setOnClickListener {
            if(AddMedicineNotice.isChecked_Mbefore) {
                btn_addNotice_MbeforeMeal.setImageResource(R.drawable.before_meal)
                btn_addNotice_MafterMeal.setImageResource(R.drawable.after_meal_clicked)

                AddMedicineNotice.isChecked_Mbefore = false
                AddMedicineNotice.isChecked_Mafter = true
            }
        }

        txt_in_hour.setOnClickListener{
            if(isChecked_in30m==true){
                txt_in_hour.setText("30분 이후")
                isChecked_in30m=false
            }
            else if(isChecked_in30m==false){
                txt_in_hour.setText("30분 이전")
                isChecked_in30m=true
            }

        }

        OnClickTime()

        var btn_cancel = findViewById<ImageButton>(R.id.btn_cancel)
        btn_cancel.setOnClickListener {
            startActivity(Intent(this, MedicineNotice::class.java))
        }

        var btn_save = findViewById<ImageButton>(R.id.btn_save)
        btn_save.setOnClickListener {
            startActivity(Intent(this, MedicineNotice::class.java))
            Toast.makeText(this, "알람이 저장되었습니다", Toast.LENGTH_LONG).show()
            //현재 알람 저장하고
            addDatabaseUpdate()
        }

    }

    private fun OnClickTime() {
        val textView2 = findViewById<TextView>(R.id.textView2)
        val timePicker = findViewById<TimePicker>(R.id.timePickerM)
        timePicker.setOnTimeChangedListener(TimePicker.OnTimeChangedListener { timePicker, hour, minute ->
            var hour = hour
            var am_pm: String ?=null
            if(hour>12){
                hour=hour-12
                am_pm=" PM"}
            else{am_pm=" AM"}
            textView2.text = hour.toString() + " : " + minute.toString() + am_pm
        })

        val textView3 = findViewById<TextView>(R.id.textView3)
        val timePicker2 = findViewById<TimePicker>(R.id.timePickerM2)
        timePicker2.setOnTimeChangedListener(TimePicker.OnTimeChangedListener { timePicker, hour, minute ->
            var hour = hour
            var am_pm: String ?=null
            if(hour>12){
                hour=hour-12
                am_pm=" PM"}
            else{am_pm=" AM"}
            textView3.text = hour.toString() +" : "+ minute.toString() + am_pm
        })

        val textView4 = findViewById<TextView>(R.id.textView4)
        val timePicker3 = findViewById<TimePicker>(R.id.timePickerM3)
        timePicker3.setOnTimeChangedListener(TimePicker.OnTimeChangedListener { timePicker, hour, minute ->
            var hour = hour
            var am_pm: String ?=null
            if(hour>12){
                hour=hour-12
                am_pm=" PM"}
            else{am_pm=" AM"}
            textView4.text = hour.toString() +" : "+ minute.toString() + am_pm
        })

    }//OnClickTime

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addDatabaseUpdate() {

        val presentMAlarmN = findViewById<TextView>(R.id.editTxt_MaDayNum).text.toString()
        var intPresentMAlarmN: Int = Integer.valueOf(presentMAlarmN)
        howManyInOnce = findViewById<TextView>(R.id.editTxt_aDayNum2).text.toString()

        pillName = findViewById<TextView>(R.id.pillName).text.toString()

        if (isChecked_Mbefore == true) {
            MealBeforeOrAfter = "before"
        } else if (isChecked_Mafter == true) {
            MealBeforeOrAfter = "after"
        }

        if (isChecked_in30m == true) {
            howMuchTimeBeforeOrAfter = "30분이내"
        } else if (isChecked_Mafter == true) {
            howMuchTimeBeforeOrAfter = "30분이후"
        }

        if (intPresentMAlarmN==1) {
            alarm_1_Time=textView2.text.toString()
        }

        if (intPresentMAlarmN==2) {
            alarm_1_Time=textView2.text.toString()
            alarm_2_Time=textView3.text.toString()
        }

        if (intPresentMAlarmN==3) {
            alarm_1_Time=textView2.text.toString()
            alarm_2_Time=textView3.text.toString()
            alarm_3_Time=textView4.text.toString()
        }

        var pillDTO = PillDTO(
            "user1",
            pillName,
            presentMAlarmN,
            howManyInOnce,

            MealBeforeOrAfter,
            "30분이내",

            alarm_1_Time,
            alarm_2_Time,
            alarm_3_Time
        )

        var pillTimeDTO_In = PillTimeDTO(
            pillName,
            pillName2,
            pillName3,
            pillName4
        )

        if (intPresentMAlarmN==1) {
            val document = alarm_2_Time
            firestore = FirebaseFirestore.getInstance()
            firestore?.collection("pillAlarm")?.document(document.toString())?.get()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var pillTimeDTO = task.result?.toObject(PillTimeDTO::class.java)
                        if(pillTimeDTO != null) {
                            if (pillTimeDTO.pillName2 ==null){
                                firestore?.collection("pillAlarm")?.document(document.toString())?.update("pillName2", document.toString())
                                    ?.addOnSuccessListener {  Toast.makeText(this, "Firebase1 update Success", Toast.LENGTH_LONG).show() }
                                    ?.addOnFailureListener {  Toast.makeText(this, "Firebase1 update Failed", Toast.LENGTH_LONG).show() }
                            }//pillTimeDTO?.pillName2==null

                            else if (pillTimeDTO.pillName3 ==null){
                                firestore?.collection("pillAlarm")?.document(document.toString())?.update("pillName3", document.toString())
                                    ?.addOnSuccessListener {  Toast.makeText(this, "Firebase1 update Success", Toast.LENGTH_LONG).show() }
                                    ?.addOnFailureListener {  Toast.makeText(this, "Firebase1 update Failed", Toast.LENGTH_LONG).show() }
                            }//pillTimeDTO?.pillName3==null

                            else if (pillTimeDTO.pillName4 ==null){
                                firestore?.collection("pillAlarm")?.document(document.toString())?.update("pillName4", document.toString())
                                    ?.addOnSuccessListener {  Toast.makeText(this, "Firebase1 update Success", Toast.LENGTH_LONG).show() }
                                    ?.addOnFailureListener {  Toast.makeText(this, "Firebase1 update Failed", Toast.LENGTH_LONG).show() }
                            }//pillTimeDTO?.pillName4==null

                            else{
                                Toast.makeText(this, "최대 4개 한도 도달하여 알람 추가 불가", Toast.LENGTH_LONG).show()
                            }
                        }//if(task != null)
                        //No updates + new Time
                        else {
                            firestore?.collection("pillAlarm")?.document(document.toString())
                                ?.set(pillTimeDTO_In)?.addOnCompleteListener {
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
                            Toast.makeText(this, "No updates1-1 + new Time", Toast.LENGTH_LONG).show()
                        }//No updates + new Time
                    }else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }//fin of (intPresentMAlarmN==1) ?.addOnCompleteListener {

            firestore?.collection("pillAlarm")?.document(pillName.toString())
                ?.set(pillDTO)?.addOnCompleteListener {
                        task ->
                    //progressBarAdd.visibility = View.GONE
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Firebase1 Success", Toast.LENGTH_LONG).show()
                        //txtAddResult.text = "Insert Success"
                    }
                    else {
                        Toast.makeText(this, "Firebase1 Failed", Toast.LENGTH_LONG).show()
                        //txtAddResult.text = task.exception?.message
                    }
                }
        }//fin of (intPresentMAlarmN==1)

        else if (intPresentMAlarmN==2) {
            val document1 = alarm_1_Time
            firestore = FirebaseFirestore.getInstance()
            firestore?.collection("pillAlarm")?.document(document1.toString())?.get()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var pillTimeDTO = task.result?.toObject(PillTimeDTO::class.java)
                        if(pillTimeDTO != null) {
                            if (pillTimeDTO.pillName2 == null) {
                                firestore?.collection("pillAlarm")?.document(document1.toString())
                                    ?.update("pillName2", document1.toString())
                                    ?.addOnSuccessListener {
                                        Toast.makeText(
                                            this,
                                            "Firebase2-1 update Success",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    ?.addOnFailureListener {
                                        Toast.makeText(
                                            this,
                                            "Firebase2-1 update Failed",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                            }//pillTimeDTO?.pillName2==null

                            else if (pillTimeDTO.pillName3 == null) {
                                firestore?.collection("pillAlarm")?.document(document1.toString())
                                    ?.update("pillName3", document1.toString())
                                    ?.addOnSuccessListener {
                                        Toast.makeText(
                                            this,
                                            "Firebase2-1 update Success",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    ?.addOnFailureListener {
                                        Toast.makeText(
                                            this,
                                            "Firebase2-1 update Failed",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                            }//pillTimeDTO?.pillName3==null

                            else if (pillTimeDTO.pillName4 == null) {
                                firestore?.collection("pillAlarm")?.document(document1.toString())
                                    ?.update("pillName4", document1.toString())
                                    ?.addOnSuccessListener {
                                        Toast.makeText(
                                            this,
                                            "Firebase2-1 update Success",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    ?.addOnFailureListener {
                                        Toast.makeText(
                                            this,
                                            "Firebase2-1 update Failed",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                            }//pillTimeDTO?.pillName4==null
                            else {
                                Toast.makeText(this, "최대 4개 한도 도달하여 알람 추가 불가", Toast.LENGTH_LONG).show()
                            }
                        }//if(task != null)

                        //No updates + new Time
                        else {
                            firestore?.collection("pillAlarm")?.document(document1.toString())
                                ?.set(pillTimeDTO_In)?.addOnCompleteListener { task ->
                                    //progressBarAdd.visibility = View.GONE
                                    if (task.isSuccessful) {
                                        Toast.makeText(this, "Firebase2-1 Success", Toast.LENGTH_LONG)
                                            .show()
                                        //txtAddResult.text = "Insert Success"
                                    } else {
                                        Toast.makeText(this, "Firebase2-1 Failed", Toast.LENGTH_LONG)
                                            .show()
                                        //txtAddResult.text = task.exception?.message
                                    }
                                }
                            Toast.makeText(this, "No updates2-1 + new Time", Toast.LENGTH_LONG).show()
                        }//No updates + new Time
                    }else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }//fin of (intPresentMAlarmN==2)-1 ?.addOnCompleteListener

            firestore?.collection("pillAlarm")?.document(pillName.toString())
                ?.set(pillDTO)?.addOnCompleteListener {
                        task ->
                    //progressBarAdd.visibility = View.GONE
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Firebase2-1 Success", Toast.LENGTH_LONG).show()
                        //txtAddResult.text = "Insert Success"
                    } else {
                        Toast.makeText(this, "Firebase2-1 Failed", Toast.LENGTH_LONG).show()
                        //txtAddResult.text = task.exception?.message
                    }
                }

            val document2 = alarm_3_Time
            firestore2 = FirebaseFirestore.getInstance()
            firestore2?.collection("pillAlarm")?.document(document2.toString())?.get()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var pillTimeDTO = task.result?.toObject(PillTimeDTO::class.java)
                        if(pillTimeDTO != null) {
                            if (pillTimeDTO.pillName2 == null) {
                                firestore2?.collection("pillAlarm")
                                    ?.document(document2.toString())
                                    ?.update("pillName2", document2.toString())
                                    ?.addOnSuccessListener {
                                        Toast.makeText(
                                            this,
                                            "Firebase2-2 update Success",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    ?.addOnFailureListener {
                                        Toast.makeText(
                                            this,
                                            "Firebase2-2 update Failed",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                            }//pillTimeDTO?.pillName2==null

                            else if (pillTimeDTO.pillName3 == null) {
                                firestore2?.collection("pillAlarm")
                                    ?.document(document2.toString())
                                    ?.update("pillName3", document2.toString())
                                    ?.addOnSuccessListener {
                                        Toast.makeText(
                                            this,
                                            "Firebase2-2 update Success",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    ?.addOnFailureListener {
                                        Toast.makeText(
                                            this,
                                            "Firebase2-2 update Failed",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                            }//pillTimeDTO?.pillName3==null

                            else if (pillTimeDTO.pillName4 == null) {
                                firestore2?.collection("pillAlarm")
                                    ?.document(document2.toString())
                                    ?.update("pillName4", document2.toString())
                                    ?.addOnSuccessListener {
                                        Toast.makeText(
                                            this,
                                            "Firebase2-2 update Success",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    ?.addOnFailureListener {
                                        Toast.makeText(
                                            this,
                                            "Firebase2-2 update Failed",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                            }//pillTimeDTO?.pillName4==null
                            else {
                                Toast.makeText(
                                    this,
                                    "최대 4개 한도 도달하여 알람 추가 불가",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }//if(task != null)

                        //No updates + new Time
                        else {
                            firestore2?.collection("pillAlarm")?.document(document2.toString())
                                ?.set(pillTimeDTO_In)?.addOnCompleteListener {
                                        task ->
                                    //progressBarAdd.visibility = View.GONE
                                    if (task.isSuccessful) {
                                        Toast.makeText(this, "Firebase2-2 Success", Toast.LENGTH_LONG).show()
                                        //txtAddResult.text = "Insert Success"
                                    } else {
                                        Toast.makeText(this, "Firebase2-2 Failed", Toast.LENGTH_LONG).show()
                                        //txtAddResult.text = task.exception?.message
                                    }
                                }
                            Toast.makeText(this, "No updates2-2 + new Time", Toast.LENGTH_LONG).show()
                        }//No updates + new Time
                    }else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }//fin of (intPresentMAlarmN==2)-2 ?.addOnCompleteListener


            firestore2?.collection("pillAlarm")?.document(pillName.toString())
                ?.set(pillDTO)?.addOnCompleteListener {
                        task ->
                    //progressBarAdd.visibility = View.GONE
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Firebase2-2 Success", Toast.LENGTH_LONG).show()
                        //txtAddResult.text = "Insert Success"
                    } else {
                        Toast.makeText(this, "Firebase2-2 Failed", Toast.LENGTH_LONG).show()
                        //txtAddResult.text = task.exception?.message
                    }
                }
        }//fin of (intPresentMAlarmN==2)

        else if (intPresentMAlarmN >=3) {
            val document1 = alarm_1_Time
            firestore = FirebaseFirestore.getInstance()
            firestore?.collection("pillAlarm")?.document(document1.toString())?.get()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var pillTimeDTO = task.result?.toObject(PillTimeDTO::class.java)
                        if(pillTimeDTO != null) {
                            if (pillTimeDTO.pillName2 ==null){
                                firestore?.collection("pillAlarm")?.document(document1.toString())?.update("pillName2", document1.toString())
                                    ?.addOnSuccessListener {  Toast.makeText(this, "Firebase3-1 update Success", Toast.LENGTH_LONG).show() }
                                    ?.addOnFailureListener {  Toast.makeText(this, "Firebase3-1 update Failed", Toast.LENGTH_LONG).show() }
                            }//pillTimeDTO?.pillName2==null

                            else if (pillTimeDTO.pillName3 ==null){
                                firestore?.collection("pillAlarm")?.document(document1.toString())?.update("pillName3", document1.toString())
                                    ?.addOnSuccessListener {  Toast.makeText(this, "Firebase3-1 update Success", Toast.LENGTH_LONG).show() }
                                    ?.addOnFailureListener {  Toast.makeText(this, "Firebase3-1 update Failed", Toast.LENGTH_LONG).show() }
                            }//pillTimeDTO?.pillName3==null

                            else if (pillTimeDTO.pillName4 ==null){
                                firestore?.collection("pillAlarm")?.document(document1.toString())?.update("pillName4", document1.toString())
                                    ?.addOnSuccessListener {  Toast.makeText(this, "Firebase3-1 update Success", Toast.LENGTH_LONG).show() }
                                    ?.addOnFailureListener {  Toast.makeText(this, "Firebase3-1 update Failed", Toast.LENGTH_LONG).show() }
                            }//pillTimeDTO?.pillName4==null

                            else{
                                Toast.makeText(this, "최대 4개 한도 도달하여 알람 추가 불가", Toast.LENGTH_LONG).show()
                            }
                        }//if(task != null)

                        //No updates + new Time
                        else {
                            firestore?.collection("pillAlarm")?.document(document1.toString())
                                ?.set(pillTimeDTO_In)?.addOnCompleteListener {
                                        task ->
                                    //progressBarAdd.visibility = View.GONE
                                    if (task.isSuccessful) {
                                        Toast.makeText(this, "Firebase3-1 Success", Toast.LENGTH_LONG).show()
                                        //txtAddResult.text = "Insert Success"
                                    } else {
                                        Toast.makeText(this, "Firebase3-1 Failed", Toast.LENGTH_LONG).show()
                                        //txtAddResult.text = task.exception?.message
                                    }
                                }
                            Toast.makeText(this, "No updates3-1 + new Time", Toast.LENGTH_LONG).show()
                        }//No updates + new Time
                    }else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }//fin of (intPresentMAlarmN==3)-1 ?.addOnCompleteListener

            firestore?.collection("pillAlarm")?.document(pillName.toString())
                ?.set(pillDTO)?.addOnCompleteListener {
                        task ->
                    //progressBarAdd.visibility = View.GONE
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Firebase3-1 Success", Toast.LENGTH_LONG).show()
                        //txtAddResult.text = "Insert Success"
                    } else {
                        Toast.makeText(this, "Firebase3-1 Failed", Toast.LENGTH_LONG).show()
                        //txtAddResult.text = task.exception?.message
                    }
                }

            val document2 = alarm_2_Time
            firestore2 = FirebaseFirestore.getInstance()
            firestore2?.collection("pillAlarm")?.document(document2.toString())?.get()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var pillTimeDTO = task.result?.toObject(PillTimeDTO::class.java)
                        if(pillTimeDTO != null) {
                            if (pillTimeDTO.pillName2 ==null){
                                firestore2?.collection("pillAlarm")?.document(document2.toString())?.update("pillName2", document2.toString())
                                    ?.addOnSuccessListener {  Toast.makeText(this, "Firebase3-2 update Success", Toast.LENGTH_LONG).show() }
                                    ?.addOnFailureListener {  Toast.makeText(this, "Firebase3-2 update Failed", Toast.LENGTH_LONG).show() }
                            }//pillTimeDTO?.pillName2==null

                            else if (pillTimeDTO.pillName3 ==null){
                                firestore2?.collection("pillAlarm")?.document(document2.toString())?.update("pillName3", document2.toString())
                                    ?.addOnSuccessListener {  Toast.makeText(this, "Firebase3-2 update Success", Toast.LENGTH_LONG).show() }
                                    ?.addOnFailureListener {  Toast.makeText(this, "Firebase3-2 update Failed", Toast.LENGTH_LONG).show() }
                            }//pillTimeDTO?.pillName3==null

                            else if (pillTimeDTO.pillName4 ==null){
                                firestore2?.collection("pillAlarm")?.document(document2.toString())?.update("pillName4", document2.toString())
                                    ?.addOnSuccessListener {  Toast.makeText(this, "Firebase3-2 update Success", Toast.LENGTH_LONG).show() }
                                    ?.addOnFailureListener {  Toast.makeText(this, "Firebase3-2 update Failed", Toast.LENGTH_LONG).show() }
                            }//pillTimeDTO?.pillName4==null

                            else{
                                Toast.makeText(this, "최대 4개 한도 도달하여 알람 추가 불가", Toast.LENGTH_LONG).show()
                            }
                        }//if(task != null)
                        //No updates + new Time
                        else {
                            firestore2?.collection("pillAlarm")?.document(document2.toString())
                                ?.set(pillTimeDTO_In)?.addOnCompleteListener {
                                        task ->
                                    //progressBarAdd.visibility = View.GONE
                                    if (task.isSuccessful) {
                                        Toast.makeText(this, "Firebase3-2 Success", Toast.LENGTH_LONG).show()
                                        //txtAddResult.text = "Insert Success"
                                    } else {
                                        Toast.makeText(this, "Firebase3-2 Failed", Toast.LENGTH_LONG).show()
                                        //txtAddResult.text = task.exception?.message
                                    }
                                }
                            Toast.makeText(this, "No updates3-2 + new Time", Toast.LENGTH_LONG).show()
                        }//No updates + new Time
                    }else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }//fin of (intPresentMAlarmN==3)-2 ?.addOnCompleteListener

            firestore2?.collection("pillAlarm")?.document(pillName.toString())
                ?.set(pillDTO)?.addOnCompleteListener {
                        task ->
                    //progressBarAdd.visibility = View.GONE
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Firebase3-2 Success", Toast.LENGTH_LONG).show()
                        //txtAddResult.text = "Insert Success"
                    } else {
                        Toast.makeText(this, "Firebase3-2 Failed", Toast.LENGTH_LONG).show()
                        //txtAddResult.text = task.exception?.message
                    }
                }

            val document3 = alarm_3_Time
            firestore3 = FirebaseFirestore.getInstance()
            firestore3?.collection("pillAlarm")?.document(document3.toString())?.get()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var pillTimeDTO = task.result?.toObject(PillTimeDTO::class.java)
                        if(pillTimeDTO != null) {
                            if (pillTimeDTO.pillName2 ==null){
                                firestore3?.collection("pillAlarm")?.document(document3.toString())?.update("pillName2", document3.toString())
                                    ?.addOnSuccessListener {  Toast.makeText(this, "Firebase3-3 update Success", Toast.LENGTH_LONG).show() }
                                    ?.addOnFailureListener {  Toast.makeText(this, "Firebase3-3 update Failed", Toast.LENGTH_LONG).show() }
                            }//pillTimeDTO?.pillName2==null

                            else if (pillTimeDTO.pillName3 ==null){
                                firestore3?.collection("pillAlarm")?.document(document3.toString())?.update("pillName3", document3.toString())
                                    ?.addOnSuccessListener {  Toast.makeText(this, "Firebase3-3 update Success", Toast.LENGTH_LONG).show() }
                                    ?.addOnFailureListener {  Toast.makeText(this, "Firebase3-3 update Failed", Toast.LENGTH_LONG).show() }
                            }//pillTimeDTO?.pillName3==null

                            else if (pillTimeDTO.pillName4 ==null){
                                firestore3?.collection("pillAlarm")?.document(document3.toString())?.update("pillName4", document3.toString())
                                    ?.addOnSuccessListener {  Toast.makeText(this, "Firebase3-3 update Success", Toast.LENGTH_LONG).show() }
                                    ?.addOnFailureListener {  Toast.makeText(this, "Firebase3-3 update Failed", Toast.LENGTH_LONG).show() }
                            }//pillTimeDTO?.pillName4==null

                            else{
                                Toast.makeText(this, "최대 4개 한도 도달하여 알람 추가 불가", Toast.LENGTH_LONG).show()
                            }
                        }//if(task != null)

                        //No updates + new Time
                        else {
                            firestore3?.collection("pillAlarm")?.document(document3.toString())
                                ?.set(pillTimeDTO_In)?.addOnCompleteListener {
                                        task ->
                                    //progressBarAdd.visibility = View.GONE
                                    if (task.isSuccessful) {
                                        Toast.makeText(this, "Firebase3-3 Success", Toast.LENGTH_LONG).show()
                                        //txtAddResult.text = "Insert Success"
                                    } else {
                                        Toast.makeText(this, "Firebase3-3 Failed", Toast.LENGTH_LONG).show()
                                        //txtAddResult.text = task.exception?.message
                                    }
                                }
                            Toast.makeText(this, "No updates3-3 + new Time", Toast.LENGTH_LONG).show()
                        }//No updates + new Time
                    }else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }//fin of (intPresentMAlarmN==3)-3 ?.addOnCompleteListener

            firestore3?.collection("pillAlarm")?.document(pillName.toString())
                ?.set(pillDTO)?.addOnCompleteListener {
                        task ->
                    //progressBarAdd.visibility = View.GONE
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Firebase3-3 Success", Toast.LENGTH_LONG).show()
                        //txtAddResult.text = "Insert Success"
                    } else {
                        Toast.makeText(this, "Firebase3-3 Failed", Toast.LENGTH_LONG).show()
                        //txtAddResult.text = task.exception?.message
                    }
                }
        }//fin of (intPresentMAlarmN>=3)

    }

}
