package com.example.ssokk20ex

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {
    private val introAdpter = IntroAdapter(
        listOf(
            IntroSlide(R.drawable.intro01),
            IntroSlide(R.drawable.intro02),
            IntroSlide(R.drawable.intro03),
            IntroSlide(R.drawable.intro04)
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pref = getSharedPreferences("pref", Context.MODE_PRIVATE)
//        if(!pref.getBoolean("isNew", true)) {
//            if(pref.getBoolean("isSignedIn", false))
//                startActivity(Intent(this, MainActivity::class.java))
//            else
//                startActivity(Intent(this, SignInActivity::class.java))
//        }

        setContentView(R.layout.activity_intro)
        supportActionBar?.hide()
        previousBtn.visibility = View.INVISIBLE
        startBtn.visibility = View.INVISIBLE

        viewPager.adapter = introAdpter
        setDots()
        setCurrentDot(0)
        viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentDot(position)
                if(position <= 0) previousBtn.visibility = View.INVISIBLE
                else previousBtn.visibility = View.VISIBLE

                if(position == introAdpter.itemCount-1)
                    startBtn.visibility = View.VISIBLE
                else
                    startBtn.visibility = View.INVISIBLE
            }
        })

        previousBtn.setOnClickListener {
            if(viewPager.currentItem <= 1 )
                previousBtn.visibility = View.INVISIBLE
            else
                previousBtn.visibility = View.VISIBLE

            viewPager.currentItem -= 1
            startBtn.visibility = View.INVISIBLE
        }

        nextBtn.setOnClickListener {
            if(viewPager.currentItem +1 == introAdpter.itemCount-1) {
                viewPager.currentItem += 1
                startBtn.visibility = View.VISIBLE
            }
            else if(viewPager.currentItem + 1 < introAdpter.itemCount) {
                viewPager.currentItem += 1
                previousBtn.visibility = View.VISIBLE
                startBtn.visibility = View.INVISIBLE
            }
            else {
                setNoMoreIntro(pref)
                startActivity(Intent(this, SignInActivity::class.java))
                startBtn.visibility = View.INVISIBLE
            }
        }

        skipBtn.setOnClickListener {
            setNoMoreIntro(pref)
            startActivity(Intent(this, SignInActivity::class.java))
        }

        startBtn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    private fun setDots() {
        val dots = arrayOfNulls<ImageView>(introAdpter.itemCount)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.setMargins(8, 0, 8, 0)
        for(i in dots.indices) {
            dots[i] = ImageView(applicationContext)
            dots[i].apply {
                this?.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext, R.drawable.dot_active
                )
            )
                this?.layoutParams = layoutParams
            }
            dotsContainer.addView(dots[i])
        }
    }

    private fun setCurrentDot(index: Int) {
        val count = dotsContainer.childCount
        for(i in 0 until count) {
            val imageView = dotsContainer[i] as ImageView
            if(i == index)
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dot_active))
            else
                imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.dot_inactive))
        }
    }

    private fun setNoMoreIntro(pref: SharedPreferences) {
        val editor = pref.edit()

        editor.putBoolean("isNew", false)
        editor.apply()
    }
}
