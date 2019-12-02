package com.hongbeomi.findtaek.view.ui.add

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.databinding.ActivityAddBinding
import com.hongbeomi.findtaek.view.AddActivity.EXTRA_CIRCULAR_REVEAL_X
import com.hongbeomi.findtaek.view.AddActivity.EXTRA_CIRCULAR_REVEAL_Y
import kotlinx.android.synthetic.main.activity_add.*
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.math.max

/**
 * @author hongbeomi
 */
class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    var revealX: Int = 0
    var revealY: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)

        binding.vm = getViewModel()
        binding.vm.also {
            it?.observeToast(this) { message -> toast(message) }
            it?.observeFinish(this) { finish() }
        }
        binding.lifecycleOwner = this

        receiveIntentFromMainActivity(savedInstanceState)
    }

    private fun receiveIntentFromMainActivity(savedInstanceState: Bundle?) {
        if (savedInstanceState == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP &&
            intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
            intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)
        ) {
            addActivityLayout.visibility = View.INVISIBLE
            revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0)
            revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0)

            val viewTreeObserver = addActivityLayout.viewTreeObserver
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        revealActivity()
                        addActivityLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
            }
        } else {
            addActivityLayout.visibility = View.VISIBLE
        }
    }

    fun revealActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val finalRadius: Float = (max(addActivityLayout.width, addActivityLayout.height) * 1.1).toFloat()
            val circularReveal = ViewAnimationUtils.createCircularReveal(
                addActivityLayout, revealX, revealY, 0F, finalRadius
            )
            circularReveal.apply {
                duration = 400
                interpolator = AccelerateInterpolator()
            }
            addActivityLayout.visibility = View.VISIBLE
            circularReveal.start()
        } else {
            finish()
        }
    }

    private fun unRevealActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val finalRadius: Float = (max(addActivityLayout.width, addActivityLayout.height) * 1.1).toFloat()
            val circularReveal = ViewAnimationUtils.createCircularReveal(
                addActivityLayout, revealX, revealY, finalRadius, 0F
            )
            circularReveal.apply {
                duration = 400
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        addActivityLayout.visibility = View.INVISIBLE
                        finish()
                    }
                })
                start()
            }
        } else {
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        unRevealActivity()
    }

}