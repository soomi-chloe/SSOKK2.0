package com.example.ssokk20ex

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ssokk20ex.ui.record.RecordBloodSugarDTO
import com.example.ssokk20ex.ui.record.RecordWeightDTO
import com.example.ssokk20ex.ui.record.UserDTO
import com.example.ssokk20ex.ui.statistics.StatisticsFunctions
import com.example.ssokk20ex.ui.statistics.StatisticsFunctionsWeight
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_record.*
import java.util.concurrent.CountDownLatch

class MainActivity : AppCompatActivity() {
    val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide() // 앱바(App Bar) 감추기.

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_record, R.id.navigation_exercise, R.id.navigation_info, R.id.navigation_myPage
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        getBloodSugarRecord()
        getWeightRecord()
        getUserInfo()
    }

    fun getBloodSugarRecord() {
        firestore.collection("record_bloodSugar").get()
            .addOnCompleteListener{ task ->
                if(task.isSuccessful) {
                    for(dc in task.result!!.documents) {
                        StatisticsFunctions.bsList.add(dc.toObject(RecordBloodSugarDTO::class.java)!!)
                    }
                }
                else
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
    }

    fun getWeightRecord() {
        firestore.collection("record_weight").get()
            .addOnCompleteListener{ task ->
                if(task.isSuccessful) {
                    for(dc in task.result!!.documents) {
                        StatisticsFunctionsWeight.weightList.add(dc.toObject(RecordWeightDTO::class.java)!!)
                    }
                }
                else
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
    }

    fun getUserInfo() {
        firestore.collection("user_info").get()
            .addOnCompleteListener{ task ->
                if(task.isSuccessful) {
                    for(dc in task.result!!.documents) {
                        StatisticsFunctionsWeight.infoList.add(dc.toObject(UserDTO::class.java)!!)
                    }
                }
                else
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
    }
}
