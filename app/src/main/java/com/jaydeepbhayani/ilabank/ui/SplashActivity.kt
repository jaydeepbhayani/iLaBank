package com.jaydeepbhayani.ilabank.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.jaydeepbhayani.ilabank.R
import com.jaydeepbhayani.ilabank.core.BaseActivity
import com.jaydeepbhayani.ilabank.ui.home.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : BaseActivity() {
    val TAG = javaClass.simpleName

    val ANIMATION_DURATION: Long = 1000
    lateinit var slideLeftToRight: Animation
    var slideDownAnimation: Animation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )*/

        setContentView(R.layout.activity_splash)

        slideDownAnimation = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.item_animation_fall_down
        )
        slideLeftToRight = AnimationUtils.loadAnimation(applicationContext,
            R.anim.item_animation_from_bottom
        )
        logoContainer.startAnimation(slideDownAnimation)
        tvCredit.startAnimation(slideLeftToRight)
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}