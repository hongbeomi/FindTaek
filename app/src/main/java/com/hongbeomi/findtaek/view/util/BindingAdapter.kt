package com.hongbeomi.findtaek.view.util

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import org.jetbrains.anko.image

@BindingAdapter("setDrawable")
fun setImage(imageView: ImageView, @DrawableRes drawableId: Int?) {
    drawableId ?: return
    imageView.image = imageView.context.getDrawable(drawableId)
}