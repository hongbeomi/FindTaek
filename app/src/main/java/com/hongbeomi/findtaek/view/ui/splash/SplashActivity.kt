package com.hongbeomi.findtaek.view.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.databinding.ActivitySplashBinding
import com.hongbeomi.findtaek.view.ui.base.BaseActivity
import com.hongbeomi.findtaek.view.ui.main.MainActivity

/**
 * @author hongbeomi
 */

class SplashActivity : BaseActivity() {

    private val binding by binding<ActivitySplashBinding>(R.layout.activity_splash)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startAnimation()
    }

    private fun startAnimation() {
        val logoAnim = AnimationUtils.loadAnimation(this, R.anim.anim_splash_logo)
        binding.imageViewSplashLogo.startAnimation(logoAnim)

        logoAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        })
    }

}