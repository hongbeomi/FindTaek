package com.hongbeomi.findtaek.view.util

import android.app.Activity
import android.view.View
import co.mobiwise.materialintro.shape.Focus
import co.mobiwise.materialintro.shape.FocusGravity
import co.mobiwise.materialintro.shape.ShapeType
import co.mobiwise.materialintro.view.MaterialIntroView

class IntroUtil {

    companion object {
        fun initIntro(
            view: View,
            activity: Activity,
            text: String,
            key: String,
            shapeType: ShapeType
        ) {
            MaterialIntroView.Builder(activity)
                .enableIcon(true)
                .setFocusGravity(FocusGravity.CENTER)
                .setFocusType(Focus.NORMAL)
                .enableFadeAnimation(true)
                .performClick(false)
                .setInfoText(text)
                .setShape(shapeType)
                .setTarget(view)
                .setIdempotent(true)
                .setUsageId(key)
                .show()
        }
    }

}