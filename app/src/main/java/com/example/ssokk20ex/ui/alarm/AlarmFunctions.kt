package com.example.ssokk20ex.ui.alarm

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ssokk20ex.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_blood_sugar_notice.*
import kotlinx.android.synthetic.main.activity_blood_sugar_notice.btn_deleteMedicineNotice
import kotlinx.android.synthetic.main.activity_blood_sugar_notice.tab_blood_sugar
import kotlinx.android.synthetic.main.activity_blood_sugar_notice.tab_pill
import kotlinx.android.synthetic.main.activity_blood_sugar_notice.txt_leftHour
import kotlinx.android.synthetic.main.activity_blood_sugar_notice.txt_startingTime1
import kotlinx.android.synthetic.main.activity_blood_sugar_notice.txt_startingTime2
import kotlinx.android.synthetic.main.activity_blood_sugar_notice.txt_startingTime3
import kotlinx.android.synthetic.main.activity_medicine_notice.*

class AlarmFunctions : AppCompatActivity() {

    //for alarm notification
    lateinit var notificationManager : NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder : Notification.Builder
    private val channelId = "package com.example.myapplication"
    private val description = "혈당 체크할 시간입니다"

    //    private var adapter: BloodSugarAdapter? = null
    private var firestore : FirebaseFirestore? = null

    var alarmTotalN :String?=null
    var alarmTotalN_Update :Int=0

    var alarm_1_Meal :String?=null
    var alarm_1_Time:String? = null

    var alarm_2_Meal :String?=null
    var alarm_2_Time:String? = null

    var alarm_3_Meal :String?=null
    var alarm_3_Time:String? = null

    var alarm_4_Meal :String?=null
    var alarm_4_Time:String? = null

    var alarm_5_Meal :String?=null
    var alarm_5_Time:String? = null

    var alarm_6_Meal :String?=null
    var alarm_6_Time:String? = null

    var mealB:Int = 0
    var mealA:Int = 0

    var mealBeforeList = ArrayList<String>()
    var mealAfterList = ArrayList<String>()

    var isChecked_delete: Boolean = false

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blood_sugar_notice)
        supportActionBar?.hide()

        //alarm
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        btn_notify.setOnClickListener {

            val intent = Intent(this, AlarmFunctions::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT)

            //layout
            val contentView = RemoteViews(packageName, R.layout.notification_layout)
            contentView.setTextViewText(R.id.tv_title, "CodeAndroid")
            contentView.setTextViewText(R.id.tv_content, "Text notification")
            //layout

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel =
                    NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this, channelId)
                    .setContentTitle("CodeAndroid")
                    .setContentText("Test Notification")
                    .setSmallIcon(R.drawable.ic_launcher_round)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            this.resources,
                            R.drawable.ic_launcher
                        )
                    )
                    .setContentIntent(pendingIntent)
            }else {
                builder = Notification.Builder(this)
                    .setContent(contentView)
//                    .setContentTitle("CodeAndroid")
//                    .setContentText("Test Notification")
                    .setSmallIcon(R.drawable.ic_launcher_round)
                    .setLargeIcon(
                        BitmapFactory.decodeResource(
                            this.resources,
                            R.drawable.ic_launcher
                        )
                    )
                    .setContentIntent(pendingIntent)
            }
            notificationManager.notify(1234,builder.build())

        }

        tab_blood_sugar.setOnClickListener {
            startActivity(Intent(this, AlarmFunctions::class.java))
        }

        tab_pill.setOnClickListener {
            startActivity(Intent(this, MedicineNotice::class.java))
        }

        updateUI("user1")

        btn_fixBloodSugarNotice.setOnClickListener {
            startActivity(Intent(this, AddBloodSugarNotice::class.java))
        }

        btn_deleteMedicineNotice.setOnClickListener {
            isChecked_delete = true
            updateUI("user1")
            isChecked_delete = false
        }
    }

    private fun updateUI(key: String) {
        if (isChecked_delete == false) {
            firestore = FirebaseFirestore.getInstance()
            firestore?.collection("bloodSugarAlarm")?.document(key)?.get()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        var bloodSugarDTO = task.result?.toObject(BloodSugarDTO::class.java)
                        alarmTotalN = bloodSugarDTO?.alarmTotalN
                        //추가
                        if (alarmTotalN == 1.toString()) {
                            alarm_1_Meal = bloodSugarDTO?.alarm_1_MealBeforeOrAfter
                            alarm_1_Time = bloodSugarDTO?.alarm_1_Time
                        } else if (alarmTotalN == 2.toString()) {
                            alarm_1_Meal = bloodSugarDTO?.alarm_1_MealBeforeOrAfter
                            alarm_1_Time = bloodSugarDTO?.alarm_1_Time
                            alarm_2_Meal = bloodSugarDTO?.alarm_2_MealBeforeOrAfter
                            alarm_2_Time = bloodSugarDTO?.alarm_2_Time
                        } else if (alarmTotalN == 3.toString()) {
                            alarm_1_Meal = bloodSugarDTO?.alarm_1_MealBeforeOrAfter
                            alarm_1_Time = bloodSugarDTO?.alarm_1_Time
                            alarm_2_Meal = bloodSugarDTO?.alarm_2_MealBeforeOrAfter
                            alarm_2_Time = bloodSugarDTO?.alarm_2_Time
                            alarm_3_Meal = bloodSugarDTO?.alarm_3_MealBeforeOrAfter
                            alarm_3_Time = bloodSugarDTO?.alarm_3_Time
                        } else if (alarmTotalN == 4.toString()) {
                            alarm_1_Meal = bloodSugarDTO?.alarm_1_MealBeforeOrAfter
                            alarm_1_Time = bloodSugarDTO?.alarm_1_Time
                            alarm_2_Meal = bloodSugarDTO?.alarm_2_MealBeforeOrAfter
                            alarm_2_Time = bloodSugarDTO?.alarm_2_Time
                            alarm_3_Meal = bloodSugarDTO?.alarm_3_MealBeforeOrAfter
                            alarm_3_Time = bloodSugarDTO?.alarm_3_Time
                            alarm_4_Meal = bloodSugarDTO?.alarm_4_MealBeforeOrAfter
                            alarm_4_Time = bloodSugarDTO?.alarm_4_Time
                        } else if (alarmTotalN == 5.toString()) {
                            alarm_1_Meal = bloodSugarDTO?.alarm_1_MealBeforeOrAfter
                            alarm_1_Time = bloodSugarDTO?.alarm_1_Time
                            alarm_2_Meal = bloodSugarDTO?.alarm_2_MealBeforeOrAfter
                            alarm_2_Time = bloodSugarDTO?.alarm_2_Time
                            alarm_3_Meal = bloodSugarDTO?.alarm_3_MealBeforeOrAfter
                            alarm_3_Time = bloodSugarDTO?.alarm_3_Time
                            alarm_4_Meal = bloodSugarDTO?.alarm_4_MealBeforeOrAfter
                            alarm_4_Time = bloodSugarDTO?.alarm_4_Time
                            alarm_5_Meal = bloodSugarDTO?.alarm_5_MealBeforeOrAfter
                            alarm_5_Time = bloodSugarDTO?.alarm_5_Time
                        } else if (alarmTotalN == 6.toString()) {
                            alarm_1_Meal = bloodSugarDTO?.alarm_1_MealBeforeOrAfter
                            alarm_1_Time = bloodSugarDTO?.alarm_1_Time
                            alarm_2_Meal = bloodSugarDTO?.alarm_1_MealBeforeOrAfter
                            alarm_2_Time = bloodSugarDTO?.alarm_2_Time
                            alarm_3_Meal = bloodSugarDTO?.alarm_3_MealBeforeOrAfter
                            alarm_3_Time = bloodSugarDTO?.alarm_3_Time
                            alarm_4_Meal = bloodSugarDTO?.alarm_4_MealBeforeOrAfter
                            alarm_4_Time = bloodSugarDTO?.alarm_4_Time
                            alarm_5_Meal = bloodSugarDTO?.alarm_5_MealBeforeOrAfter
                            alarm_5_Time = bloodSugarDTO?.alarm_5_Time
                            alarm_6_Meal = bloodSugarDTO?.alarm_6_MealBeforeOrAfter
                            alarm_6_Time = bloodSugarDTO?.alarm_6_Time
                        }
                        if (alarm_1_Meal == "before") {
                            mealBeforeList.add(alarm_1_Time.toString())
                            mealB = mealB + 1
                        } else if (alarm_1_Meal == "after") {
                            mealAfterList.add(alarm_1_Time.toString())
                            mealA = mealA + 1
                        }
                        if (alarm_2_Meal == "before") {
                            mealBeforeList.add(alarm_2_Time.toString())
                            mealB = mealB + 1
                        } else if (alarm_2_Meal == "after") {
                            mealAfterList.add(alarm_2_Time.toString())
                            mealA = mealA + 1
                        }
                        if (alarm_3_Meal == "before") {
                            mealBeforeList.add(alarm_3_Time.toString())
                            mealB = mealB + 1
                        } else if (alarm_3_Meal == "after") {
                            mealAfterList.add(alarm_3_Time.toString())
                            mealA = mealA + 1
                        }
                        if (alarm_4_Meal == "before") {
                            mealBeforeList.add(alarm_4_Time.toString())
                            mealB = mealB + 1
                        } else if (alarm_4_Meal == "after") {
                            mealAfterList.add(alarm_4_Time.toString())
                            mealA = mealA + 1
                        }
                        if (alarm_5_Meal == "before") {
                            mealBeforeList.add(alarm_5_Time.toString())
                            mealB = mealB + 1
                        } else if (alarm_5_Meal == "after") {
                            mealAfterList.add(alarm_5_Time.toString())
                            mealA = mealA + 1
                        }
                        if (alarm_6_Meal == "before") {
                            mealBeforeList.add(alarm_6_Time.toString())
                            mealB = mealB + 1
                        } else if (alarm_6_Meal == "after") {
                            mealAfterList.add(alarm_6_Time.toString())
                            mealA = mealA + 1
                        }
                        //
                        runOnUiThread() {

                            if (mealB == 0) {
                                txt_startingTime1.text = "         "
                                txt_startingTime2.text = "( 현 알람 x )"
                                txt_startingTime3.text = "         "
                            } else if (mealB == 1) {
                                txt_startingTime1.text = mealBeforeList[0]
                                txt_startingTime2.text = "         "
                                txt_startingTime3.text = "         "
                                alarmTotalN_Update = alarmTotalN_Update + 1
                            } else if (mealB == 2) {
                                txt_startingTime1.text = mealBeforeList[0]
                                txt_startingTime2.text = mealBeforeList[1]
                                txt_startingTime3.text = "         "
                                alarmTotalN_Update = alarmTotalN_Update + 2
                            } else if (mealB >= 3) {
                                txt_startingTime1.text = mealBeforeList[0]
                                txt_startingTime2.text = mealBeforeList[1]
                                txt_startingTime3.text = mealBeforeList[2]
                                alarmTotalN_Update = alarmTotalN_Update + 3
                            }

                            if (mealA == 0) {
                                txt_startingTime4.text = "         "
                                txt_startingTime5.text = "( 현 알람 x )"
                                txt_startingTime6.text = "         "
                            }
                            if (mealA == 1) {
                                txt_startingTime4.text = mealAfterList[0]
                                txt_startingTime5.text = "         "
                                txt_startingTime6.text = "         "
                                alarmTotalN_Update = alarmTotalN_Update + 1
                            } else if (mealA == 2) {
                                txt_startingTime4.text = mealAfterList[0]
                                txt_startingTime5.text = mealAfterList[1]
                                txt_startingTime6.text = "         "
                                alarmTotalN_Update = alarmTotalN_Update + 2
                            }
                            if (mealA >= 3) {
                                txt_startingTime4.text = mealAfterList[0]
                                txt_startingTime5.text = mealAfterList[1]
                                txt_startingTime6.text = mealAfterList[2]
                                alarmTotalN_Update = alarmTotalN_Update + 3
                            }

                            if (alarmTotalN_Update==0){txt_leftHour.text="0번"}
                            txt_nextNotice_pillName3.text = alarmTotalN_Update.toString()
                            txt_howManyTimes1.text = alarmTotalN_Update.toString()
                        }
                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }
        }

        else if(isChecked_delete==true){
            firestore = FirebaseFirestore.getInstance()
            firestore?.collection("bloodSugarAlarm")?.document(key)?.delete()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        runOnUiThread() {
                            txt_startingTime1.text = "         "
                            txt_startingTime2.text = "( 현 알람 x )"
                            txt_startingTime3.text = "         "

                            txt_startingTime4.text = "         "
                            txt_startingTime5.text = "( 현 알람 x )"
                            txt_startingTime6.text = "         "

                            alarmTotalN_Update=0

                            txt_leftHour.text = "0번"
                            txt_nextNotice_pillName3.text = alarmTotalN_Update.toString()
                            txt_howManyTimes1.text = alarmTotalN_Update.toString()
                        }
                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}