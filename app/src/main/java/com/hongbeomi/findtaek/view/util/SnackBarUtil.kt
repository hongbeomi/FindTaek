package com.hongbeomi.findtaek.view.util

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.hongbeomi.findtaek.R

class SnackBarUtil {

    companion object {

        @JvmStatic
        fun showSnackBar(
            view: View,
            context: Context,
            title: String,
            time: Int,
            cancel: String,
            action: () -> Unit
        ) {
            Snackbar
                .make(view, title, time)
                .setTextColor(ContextCompat.getColor(context, R.color.marine))
                .setAction(cancel) { action() }
                .setActionTextColor(Color.WHITE)
                .setBackgroundTint(context.resources.getColor(R.color.snackBarColor))
                .setGestureInsetBottomIgnored(true)
                .show()
        }

    }

}