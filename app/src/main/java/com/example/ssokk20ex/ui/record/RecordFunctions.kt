package com.example.ssokk20ex.ui.record

import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.content.Context
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.example.ssokk20ex.MyPage
import com.example.ssokk20ex.R
import com.example.ssokk20ex.ui.alarm.BloodSugarDTO
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import io.opencensus.stats.Aggregation
import kotlinx.android.synthetic.main.fragment_record.*
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.CountDownLatch
import kotlin.collections.ArrayList

class RecordFunctions : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    private var check1: Int = 0 //식전 버튼 체크 안된 상태
    private var check2: Int = 0 //식후 버튼 체크 안된 상태
    private var chart_bloodSugar: LineChart? = null
    private val entries = ArrayList<Entry>()
    private var lineChart: LineChart?= null
    private var array = booleanArrayOf(true, true, true, true, true)
    private var filePath: Uri? = null
    private var storage: FirebaseStorage? = null
    private var firestore : FirebaseFirestore? = null
    private val GALLERY_PERMISSIONS_REQUEST = 0
    private val GALLERY_IMAGE_REQUEST = 1
    var xAxisValues: List<String> = java.util.ArrayList(
        listOf("                                              ")
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_record)
        supportActionBar?.hide()
        firestore = FirebaseFirestore.getInstance()

        var setting = findViewById<ImageButton>(R.id.setting)
        setting.setOnClickListener {
            startActivity(Intent(this, MyPage::class.java))
        }

        //1. 혈당 - 식전 버튼
        btn_beforeMeal.setOnClickListener {
            beforeMeal()
        }

        //1. 혈당 - 식후 버튼
        btn_afterMeal.setOnClickListener {
            afterMeal()
        }

        var n : Int = 1
        var mealState: String =""

        //1. 혈당 - 입력 버튼
        btn_inputBloodSugar.setOnClickListener {
            if(n==7){n=1}

            //수치가 없을 경우
            if(txt_bloodSugarNumber.text.isEmpty()){
                Toast.makeText(this, "혈당 수치를 입력해주세요", Toast.LENGTH_LONG).show()
            }

            //수치가 있을 경우 - 데이터 저장
            else {
                val num = txt_bloodSugarNumber.text.toString().toInt()
                if(check1 == 1) {
                    if (num <= 100) {
                        record_feedback.setBackgroundResource(R.drawable.record_good)
                        feedback_face.setImageResource(R.drawable.good_face)
                        feedback_status.setText("정상 수치군요! 이대로 쭈욱 유지해봐요~")
                    } else if (num <= 125) {
                        record_feedback.setBackgroundResource(R.drawable.record_soso)
                        feedback_face.setImageResource(R.drawable.soso_face)
                        feedback_status.setText("정상수치를 조금 넘었어요! 조심하세요!")
                    } else {
                        record_feedback.setBackgroundResource(R.drawable.record_bad)
                        feedback_face.setImageResource(R.drawable.bad_face)
                        feedback_status.setText("위험합니다! 혈당을 낮추도록 노력하세요!")
                    }
                } else {
                    if (num <= 140) {
                        record_feedback.setBackgroundResource(R.drawable.record_good)
                        feedback_face.setImageResource(R.drawable.good_face)
                        feedback_status.setText("정상 수치군요! 이대로 쭈욱 유지해봐요~")
                    } else if (num <= 199) {
                        record_feedback.setBackgroundResource(R.drawable.record_soso)
                        feedback_face.setImageResource(R.drawable.soso_face)
                        feedback_status.setText("정상수치를 조금 넘었어요! 조심하세요!")
                    } else {
                        record_feedback.setBackgroundResource(R.drawable.record_bad)
                        feedback_face.setImageResource(R.drawable.bad_face)
                        feedback_status.setText("위험합니다! 혈당을 낮추도록 노력하세요!")
                    }
                }

                closeKeyboard()

                if(check1.equals(0) && check2.equals(0)){
                    Toast.makeText(this, "식전/식후 중 선택해주세요", Toast.LENGTH_LONG).show()
                }
                else{
                    var bloodSugar = findViewById<TextView>(R.id.txt_bloodSugarNumber)
                    var date= LocalDateTime.now(ZoneId.of("Asia/Seoul"))
                    var strnow = date.format(DateTimeFormatter.ofPattern("yyyy-M-dd"))
                    var document = strnow + "-" + n

                    //식전인 경우
                    if(check1.equals(1) && check2.equals(0)){
                        mealState = "beforeMeal"
                    }
                    //식후인 경우
                    else if(check1.equals(0) && check2.equals(1)){
                        mealState = "afterMeal"
                    }

                    val RecordBloodSugarDTO =
                        RecordBloodSugarDTO(
                            strnow.toString(),
                            n.toString(),
                            bloodSugar.text.toString(),
                            mealState
                        )

                    firestore = FirebaseFirestore.getInstance()
                    firestore?.collection("record_bloodSugar")?.document(document)
                        ?.set(RecordBloodSugarDTO)
                        ?.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                n=n+1
                                Toast.makeText(this, "저장되었습니다", Toast.LENGTH_LONG).show()
                            }
                            else {
                                Toast.makeText(this, "오류가 발생했습니다", Toast.LENGTH_LONG).show()
                            }
                        }

                    //그래프에 수치값 찍어주기
                    val  value = Integer.parseInt(txt_bloodSugarNumber.text.toString()).toFloat()
                    chartAdd(value)

                    //초기화
                    txt_bloodSugarNumber.setText(null)
                    btn_afterMeal.setImageResource(R.drawable.after_meal)
                    btn_beforeMeal.setImageResource(R.drawable.before_meal)
                }


            }
        }

        //알람 수
        //makeAlermIcon("user1")

        //2. 약 복용 버튼
        btn_todayPill1.setOnClickListener {
            pillClicked(btn_todayPill1, 0)
        }
        btn_todayPill2.setOnClickListener {
            pillClicked(btn_todayPill2, 1)
        }
        btn_todayPill3.setOnClickListener {
            pillClicked(btn_todayPill3, 2)
        }
        btn_todayPill4.setOnClickListener {
            pillClicked(btn_todayPill4, 3)
        }
        btn_todayPill5.setOnClickListener {
            pillClicked(btn_todayPill5, 4)
        }

        //3. 체중 - 입력 버튼
        btn_inputWeight.setOnClickListener {

            //체중 수치를 적지 않는 경우
            if(txt_weight.text.isEmpty()){
                Toast.makeText(this, "체중을 입력해주세요", Toast.LENGTH_LONG).show()
            }

            //체중 수치를 적은 경우 - 데이터 저장
            else{
                var weight = findViewById<TextView>(R.id.txt_weight) //입력받은 체중값
                var date= LocalDateTime.now(ZoneId.of("Asia/Seoul"))
                var strNow = date.format(DateTimeFormatter.ofPattern("yyyy-M-dd"))
                var document = strNow.toString()

                val data = RecordWeightDTO(
                    weight.text.toString(),
                    strNow.toString()
                )

                firestore = FirebaseFirestore.getInstance()
                firestore?.collection("record_weight")?.document(document)
                    ?.set(data)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "저장되었습니다", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "오류가 발생했습니다", Toast.LENGTH_LONG).show()
                        }
                    }
                txt_weight.setText(null) //수치적는 란 초기화
            }
        }

        //4. 식사 사진
        btn_addMealImage.setOnClickListener {
            if(index == 8){
                Toast.makeText(this, "오늘의 사진이 다 찼습니다", Toast.LENGTH_LONG).show()
            }
            else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), GALLERY_PERMISSIONS_REQUEST)
                startGalleryChooser()
            }
        }
    }

//    //약
//    private fun makeAlermIcon(key:String){
//        var number:String? = null
//
//        firestore = FirebaseFirestore.getInstance()
//        var document = key
//        val countDownLatch = CountDownLatch(1)
//        firestore?.collection("bloodSugarAlarm")?.document(document)
//            ?.get()?.addOnCompleteListener{task ->
//                if (task.isSuccessful) {
//                    val total = task.result?.toObject(BloodSugarDTO::class.java)
//                    number= total?.alarmTotalN.toString() //오늘 알람 개수
//                    countDownLatch.countDown()
//                }
//            }
//        countDownLatch.await()
//
//        var pillArray = arrayOf(btn_todayPill1, btn_todayPill2, btn_todayPill3, btn_todayPill4, btn_todayPill5)
//
//        for(n in 1..number!!.toInt()){
//            pillArray[n].visibility = View.VISIBLE
//        }
//
//    }


    //갤러리 호출
    private fun startGalleryChooser() {
        //ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), GALLERY_PERMISSIONS_REQUEST)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.d("permissionChecker", "checkSelfPermission is already setted")
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY_IMAGE_REQUEST)
        }
    }


    //갤러리 권한
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        when (requestCode) {
//            GALLERY_PERMISSIONS_REQUEST  ->
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Log.d("onRequestPermission", "GALLERY_PERMISSIONS_REQUEST is granted")
//                    startGalleryChooser()
//                }
//                else {
//                    Toast.makeText(this, "퍼미션이 설정되지 않았습니다. 앱을 종료하고 다시 실행하세요", Toast.LENGTH_LONG).show()
//                }
//        }
//    }

    var index:Int = 0 //사진 들어갈 자리 인덱스

    @TargetApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var imageArray = arrayOf(mealImage1, mealImage2, mealImage3, mealImage4, mealImage5, mealImage6, mealImage7, mealImage8)

        //갤러리로 연결할 경우
        if (requestCode == GALLERY_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            Log.d("onActivityResult", imageUri.toString())

            imageArray[index].setImageURI(imageUri)
            imageArray[index].visibility = View.VISIBLE
            index += 1
            imageUploader(imageUri)
        }
    }

    //사진 데이터 저장하기
    @RequiresApi(Build.VERSION_CODES.O)
    private fun imageUploader (imageUri: Uri?) {
        storage = FirebaseStorage.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_.png"
        val storageRef = storage?.reference?.child("images")?.child(imageFileName)

//        var countDownLatch = CountDownLatch(1)
        storageRef?.putFile(imageUri!!)?.continueWithTask{
            storageRef.downloadUrl
        }?.addOnSuccessListener { taskSnapshot ->
            val downloadUrl = taskSnapshot.toString()
            var date= LocalDate.now()
            var document = date.format(DateTimeFormatter.ofPattern("yyyy-M-dd"))
            val imageDTO = RecordImageDTO(document, downloadUrl)
            firestore?.collection("record_Meal")?.document(document)
                ?.set(imageDTO)?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //Toast.makeText(this, "storage and firestore insert success", Toast.LENGTH_LONG).show()
//                        countDownLatch.countDown()
                    }
                    else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
//                        countDownLatch.countDown()
                    }
                }
        }
//        countDownLatch.await()
    }

    //키보드 내리기
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    private fun closeKeyboard()
    {
        var view = this.currentFocus
        if(view != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    //오늘의 혈당 그래프
    private fun chartAdd(value : Float) {
        val entricount = entries.count().toFloat()
        entries.add(Entry(entricount, value))
        lineChart = findViewById<LineChart>(R.id.chart_bloodSugar)


        val lineDataSet = LineDataSet(entries, "오늘의 혈당 수치")
        lineDataSet.lineWidth = 1f
        lineDataSet.circleRadius = 3f
        lineDataSet.setCircleColor(Color.parseColor("#f16161"))
        lineDataSet.color = Color.parseColor("#f16161")
        lineDataSet.setDrawCircleHole(false)
        lineDataSet.setDrawCircles(true)
        lineDataSet.setDrawHorizontalHighlightIndicator(false)
        lineDataSet.setDrawHighlightIndicators(false)
        lineDataSet.setDrawValues(false)

        val lineData = LineData(lineDataSet)
        lineChart?.data = lineData

        val xAxis: XAxis? = lineChart!!.xAxis
        xAxis?.valueFormatter = IndexAxisValueFormatter(xAxisValues)
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.granularity = 1f
        xAxis?.textColor = Color.BLACK
        xAxis?.setDrawGridLines(false)
        xAxis?.enableGridDashedLine(8f, 24f, 0f)

        val yLAxis: YAxis? = lineChart?.axisLeft
        yLAxis?.textColor = Color.BLACK
        yLAxis?.granularity = 1f
        yLAxis?.setDrawGridLines(false)

        val yRAxis: YAxis? = chart_bloodSugar?.axisRight
        yRAxis?.isEnabled = false

        val description = Description()
        description.text = ""

        lineChart!!.legend.isEnabled = false
        lineChart!!.description.isEnabled = false

        lineChart?.invalidate()
    }


    //식전 버튼
    @RequiresApi(Build.VERSION_CODES.O)
    private fun beforeMeal(){
        //체크 안된 상태
        if (check1 == 0) {
            btn_beforeMeal.setImageResource(R.drawable.before_meal_clicked)
            check1 = 1
            if(check2==1){
                btn_afterMeal.setImageResource(R.drawable.after_meal)
                check2=0
            }

        }
        //체크된 상태
        else if (check1 == 1) {
            btn_beforeMeal.setImageResource(R.drawable.before_meal)
            check1 = 0
        }
    }

    //식후 버튼
    @RequiresApi(Build.VERSION_CODES.O)
    private fun afterMeal(){
        //체크 안된 상태
        if (check2 == 0) {
            btn_afterMeal.setImageResource(R.drawable.after_meal_clicked)
            check2 = 1
            if(check1==1){
                btn_beforeMeal.setImageResource(R.drawable.before_meal)
                check1=0
            }
        }
        //체크된 상태
        else if (check2 == 1) {
            btn_afterMeal.setImageResource(R.drawable.after_meal)
            check2 = 0
        }
    }

    // 알약 아이콘
    private fun pillClicked(btn: ImageButton, index: Int){
        if(array[index] == false){
            btn.setImageResource(R.drawable.record_medi_unchecked)
            array[index] = true
        }
        else{
            btn.setImageResource(R.drawable.record_medi_checked)
            array[index] = false
        }
    }
}