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
import com.example.ssokk20ex.ui.statistics.StatisticsFunctions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_record.*

class MainActivity : AppCompatActivity() {

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
    }

    fun getBloodSugarRecord() {
        val firestore = FirebaseFirestore.getInstance()
        firestore.collection("record_bloodSugar").get()
            .addOnCompleteListener{ task ->
                if(task.isSuccessful) {
                    for(dc in task.result!!.documents) {
                        StatisticsFunctions.bsList.add(dc.toObject(RecordBloodSugarDTO::class.java)!!)
                    }
                    Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
                }
                else
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
            }
    }
}
