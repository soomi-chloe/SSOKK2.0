package com.example.ssokk20ex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {
    private val introAapter = IntroAdapter(
        listOf(
            IntroSlide(R.drawable.intro02),
            IntroSlide(R.drawable.intro03)
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        supportActionBar?.hide()

        viewPager.adapter = introAapter
        setDots()
        setCurrentDot(0)
        viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentDot(position)
            }
        })

        nextBtn.setOnClickListener {
            if(viewPager.currentItem + 1 < introAapter.itemCount)
                viewPager.currentItem += 1
            else {
                var intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
        }

        skipBtn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    private fun setDots() {
        val dots = arrayOfNulls<ImageView>(introAapter.itemCount)
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
}
