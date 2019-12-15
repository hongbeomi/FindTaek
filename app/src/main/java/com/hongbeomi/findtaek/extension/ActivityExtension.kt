package com.hongbeomi.findtaek.extension

import android.R
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hongbeomi.findtaek.view.ui.add.AddActivity
import kotlinx.android.synthetic.main.activity_add.*
import org.jetbrains.anko.intentFor
import kotlin.math.max
import com.hongbeomi.findtaek.util.AddActivity.EXTRA_CIRCULAR_REVEAL_X
import com.hongbeomi.findtaek.util.AddActivity.EXTRA_CIRCULAR_REVEAL_Y
import com.hongbeomi.findtaek.view.ui.add.AddActivity.Companion.revealX
import com.hongbeomi.findtaek.view.ui.add.AddActivity.Companion.revealY
import com.hongbeomi.findtaek.view.ui.main.MainViewModel

/**
 * @author hongbeomi
 */

fun isMinVersionLOLLIPOP() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

fun AppCompatActivity.revealActivity(layout: LinearLayout, x: Int, y: Int) {
    if (isMinVersionLOLLIPOP()) {
        val finalRadius: Float =
            (max(addActivityLayout.width, addActivityLayout.height) * 1.1).toFloat()
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            layout, x, y, 0F, finalRadius
        )
        circularReveal.apply {
            duration = 400
            interpolator = AccelerateInterpolator()
            start()
        }
        addActivityLayout.visibility = View.VISIBLE
    } else {
        finish()
    }
}

fun AppCompatActivity.unRevealActivity(layout: LinearLayout, x: Int, y: Int) {
    if (isMinVersionLOLLIPOP()) {
        val finalRadius: Float =
            (max(addActivityLayout.width, addActivityLayout.height) * 1.1).toFloat()
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            layout, x, y, finalRadius, 0F
        )
        circularReveal.apply {
            duration = 400
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    layout.visibility = View.INVISIBLE
                    finish()
                }
            })
            start()
        }
    } else {
        finish()
    }
}

fun AppCompatActivity.sendFabButtonCoordinates(view: View) {
    val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
        this, view, "transition"
    )
    startActivity(
        intentFor<AddActivity>(
            EXTRA_CIRCULAR_REVEAL_X to (view.x + view.width / 2).toInt(),
            EXTRA_CIRCULAR_REVEAL_Y to (view.y + view.height / 2).toInt()
        ),
        options.toBundle()
    )
}

fun AppCompatActivity.receiveIntentFromMainActivity(savedInstanceState: Bundle?) {
    if (savedInstanceState == null &&
        isMinVersionLOLLIPOP() &&
        intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
        intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y)
    ) {
        addActivityLayout.visibility = View.INVISIBLE
        revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0)
        revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0)

        val viewTreeObserver = addActivityLayout.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    revealActivity(addActivityLayout, revealX, revealY)
                    addActivityLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }
    } else {
        addActivityLayout.visibility = View.VISIBLE
    }
}

fun swipeRefreshRecyclerView(swipeRefreshLayout: SwipeRefreshLayout, mainViewModel: MainViewModel) {
    swipeRefreshLayout.apply {
        setColorSchemeResources(R.color.holo_blue_light)
        setOnRefreshListener {
            mainViewModel.update()
            swipeRefreshLayout.isRefreshing = false
        }
    }
}
