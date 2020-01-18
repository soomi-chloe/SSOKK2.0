package com.example.ssokk20ex.ui.record

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Camera
import android.graphics.Color
import android.media.audiofx.EnvironmentalReverb
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.ssokk20ex.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_record.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.jar.Manifest

class RecordFunctions : AppCompatActivity() {

    private var firestore : FirebaseFirestore? = null

    @RequiresApi(Build.VERSION_CODES.O)
    var date: LocalDate = LocalDate.now() //날짜 받아오기(document)
    private var check1: Int = 0 //식전 버튼 체크 안된 상태
    private var check2: Int = 0 //식후 버튼 체크 안된 상태
    private var chart_bloodSugar: LineChart? = null
    private val entries = ArrayList<Entry>()
    private var lineChart: LineChart?= null
    private var xValue: String = ""
    private var array = booleanArrayOf(true, true, true, true, true)

    var currentPath: String? = null
    val TAKE_PICTURE = 1

    var xAxisValues: List<String> = java.util.ArrayList(
        listOf(

        )
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_record)
        supportActionBar?.hide()
        firestore = FirebaseFirestore.getInstance()


        //1. 혈당 - 식전 버튼
        btn_beforeMeal.setOnClickListener {
            beforeMeal()
        }

        //1. 혈당 - 식후 버튼
        btn_afterMeal.setOnClickListener {
            afterMeal()
        }

        //1. 혈당 - 입력 버튼
        btn_inputBloodSugar.setOnClickListener {
            //그래프에 수치값 찍어주기
            val  value = Integer.parseInt(txt_bloodSugarNumber.text.toString()).toFloat()
            chartAdd(value)
            txt_bloodSugarNumber.setText(null) //수치적는 란 초기화

            addBloodSugarData() //데이터 저장
        }

        //1. 혈당 - 그래프
        chartSet()

        //2. 약
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
            addWeightData() //체중 데이터 저장
        }

        //4. 식사 사진
//        add_mealImage.setOnClickListener {
//            dispatchCameraIntent()
//        }
    }

//    fun dispatchCameraIntent(){
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        if(intent.resolveActivity(packageManager) != null){
//            var photoFile: File? = null
//            try{
//                photoFile = createImage()
//            }
//            catch(e: IOException){ }
//            if(photoFile != null){
//                var photoUri = FileProvider.getUriForFile(this,
//                    "com.coutocode.caneraexample.fileprovider", photoFile)
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
//                startActivityForResult(intent, TAKE_PICTURE)
//            }
//        }
//    }
//
//    fun createImage(): File{
//        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Data())
//        val imageName = "JPEG_" + timeStamp + "_"
//        var storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        var image = File.createTempFile(imageName, ".jpg", storageDir)
//        currentPath = image.absolutePath
//        return image
//    }

    //혈당 수치 데이터 저장
    @RequiresApi(Build.VERSION_CODES.O)
    private fun addBloodSugarData(){

        var bloodSugar = txt_bloodSugarNumber.text.toString() //입력받은 혈당수치(data)

        //아무것도 적지 않고 입력버튼을 누른 경우
        if(txt_bloodSugarNumber.text.isEmpty()){
            Toast.makeText(this, "혈당 수치를 입력해주세요", Toast.LENGTH_LONG).show()
        }

        firestore?.collection("record")?.document(date.toString())
            ?.set(bloodSugar)
            ?.addOnCompleteListener {
                    task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "저장되었습니다", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "오류가 발생했습니다", Toast.LENGTH_LONG).show()
                }
            }



//        val docRef = firestore?.collection("record")?.document("date")
//        docRef?.get()
//            ?.addOnSuccessListener { document ->
//                if(document != null){
//                    Log.d("exist", "not null")
//                } else{
//                    Log.d("noexist", "is null")
//                }
//            }
//            ?.addOnFailureListener { exception -> Log.d("is error", "got failed with", exception) }

    }


    //오늘의 혈당 그래프 - 추가
    private fun chartAdd(value : Float) {
        val entricount = entries.count().toFloat() //이걸 식전, 식후로 만들기
        entries.add(Entry(entricount, value))

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
        lineChart?.invalidate()
    }

    //오늘의 혈당 그래프 세트
    private fun chartSet() {
        lineChart = findViewById<LineChart>(R.id.chart_bloodSugar)

        entries.add(Entry())

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

//        val xValsDateLabel = ArrayList<String>()

//        val xValsOriginalMillis = ArrayList<Long>()
//        xValsOriginalMillis.add(1554875423736L)
//        xValsOriginalMillis.add(1555285494836L)
//        xValsOriginalMillis.add(1555295494896L)
//
//        for (i in xValsOriginalMillis) {
//            val mm = i / 60 % 60
//            val hh = i / (60 * 60) % 24
//            val mDateTime = "$hh:$mm"
//            xValsDateLabel.add(mDateTime)
//        }

//        //식전일 때
//        if(check1 == 1 || check2 == 0){
//            xValue = "식전"
//        }
//
//        //식후일 때
//        else if(check1 == 0 || check2 == 1){
//            xValue = "식후"
//        }

        val xAxis: XAxis? = lineChart!!.xAxis
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.granularity = 1f
        xAxis?.textColor = Color.BLACK
        xAxis?.enableGridDashedLine(8f, 24f, 0f)
        xAxis?.valueFormatter = IndexAxisValueFormatter(xAxisValues)
//        xAxis?.setValueFormatter { value, axis ->
//            if(xValsDateLabel.size-1 < value)
//                "0"
//            else
//                xValsDateLabel.get(value.toInt())
//        }

        val yLAxis: YAxis? = lineChart?.axisLeft
        yLAxis?.textColor = Color.BLACK

        val yRAxis: YAxis? = chart_bloodSugar?.axisRight
        yRAxis?.setDrawLabels(false)
        yRAxis?.setDrawAxisLine(false)
        yRAxis?.setDrawGridLines(false)

        val description = Description()
        description.text = ""

        lineChart?.isDoubleTapToZoomEnabled = false
        lineChart?.setDrawGridBackground(false)
        lineChart?.description = description
        lineChart?.animateY(2000, Easing.EasingOption.EaseInCubic)
        lineChart?.invalidate()
    }

    //식전 버튼
    private fun beforeMeal(){
        //체크 안된 상태
        if (check1 == 0) {
            btn_beforeMeal.setImageResource(R.drawable.before_meal_clicked)
            check1 = 1
        }
        //체크된 상태
        else if (check1 == 1) {
            btn_beforeMeal.setImageResource(R.drawable.before_meal)
            check1 = 0
        }
        btn_afterMeal.setImageResource(R.drawable.after_meal)
    }

    //식후 버튼
    private fun afterMeal(){
        //체크 안된 상태
        if (check2 == 0) {
            btn_afterMeal.setImageResource(R.drawable.after_meal_clicked)
            check2 = 1
        }
        //체크된 상태
        else if (check2 == 1) {
            btn_afterMeal.setImageResource(R.drawable.after_meal)
            check2 = 0
        }
        btn_beforeMeal.setImageResource(R.drawable.before_meal)
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




    //체중 데이터 저장
    @RequiresApi(Build.VERSION_CODES.O)
    private fun addWeightData(){

        var weight = txt_weight.text.toString() //입력받은 체중값(data)

        //아무것도 적지 않고 입력버튼을 누른 경우
        if(txt_bloodSugarNumber.text.isEmpty()){
            Toast.makeText(this, "체중을 입력해주세요", Toast.LENGTH_LONG).show()
        }

        firestore?.collection("record")?.document(date.toString())
            ?.set(weight)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "저장되었습니다", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "오류가 발생했습니다", Toast.LENGTH_LONG).show()
                }
            }
    }
}