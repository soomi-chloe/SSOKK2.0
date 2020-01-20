package com.example.ssokk20ex.ui.alarm

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.ssokk20ex.R

class AlarmFragment : Fragment() {

    private lateinit var alarmViewModel: AlarmViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        alarmViewModel =
            ViewModelProviders.of(this).get(AlarmViewModel::class.java)
        val root = inflater.inflate(R.layout.activity_blood_sugar_notice, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var intent = Intent(this.context, AlarmFunctions::class.java)
        startActivity(intent)
    }
}