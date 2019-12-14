package com.hongbeomi.findtaek.extension

import android.R
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
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
        }
        addActivityLayout.visibility = View.VISIBLE
        circularReveal.start()
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
    val revealX = (view.x + view.width / 2).toInt()
    val revealY = (view.y + view.height / 2).toInt()

    startActivity(
        intentFor<AddActivity>(
            EXTRA_CIRCULAR_REVEAL_X to revealX,
            EXTRA_CIRCULAR_REVEAL_Y to revealY
        ),
        options.toBundle()
    )
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
