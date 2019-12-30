package com.hongbeomi.findtaek.view

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.view.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        GlobalScope.launch(Dispatchers.Main) {
            splashAnimation()
        }
    }

    private fun splashAnimation() {
        val logoAnim = AnimationUtils.loadAnimation(this, R.anim.anim_splash_logo)
        splash_logo_image.startAnimation(logoAnim)

        logoAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationEnd(animation: Animation?) {
                startActivity<MainActivity>()
                finish()
            }

            override fun onAnimationRepeat(animation: Animation?) { }
            override fun onAnimationStart(animation: Animation?) { }
        })
    }

}