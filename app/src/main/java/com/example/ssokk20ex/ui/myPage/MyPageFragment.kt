package com.example.ssokk20ex.ui.myPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.ssokk20ex.R

class MyPageFragment : Fragment() {

    private lateinit var myPageViewModel: MyPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myPageViewModel =
            ViewModelProviders.of(this).get(MyPageViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_mypage, container, false)
        return root
    }
}