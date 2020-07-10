package com.hongbeomi.findtaek.view.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.daimajia.swipe.SwipeLayout
import com.github.vipulasri.timelineview.TimelineView
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.view.ui.main.MainRecyclerAdapter
import kotlinx.android.synthetic.main.item_main.view.*
import kr.co.prnd.StepProgressBar
import org.jetbrains.anko.image

/**
 * @author hongbeomi
 */

@BindingAdapter("setDrawable")
fun setImage(imageView: ImageView, @DrawableRes drawableId: Int?) {
    drawableId ?: return
    imageView.image = imageView.context.getDrawable(drawableId)
}

@BindingAdapter("dragTargetView")
fun setSwipeLayout(swipeLayout: SwipeLayout, dragTargetView: View?) {
    dragTargetView ?: return
    swipeLayout.apply {
        showMode = SwipeLayout.ShowMode.LayDown
        isLeftSwipeEnabled = false
        addDrag(SwipeLayout.DragEdge.Left, dragTargetView)
    }
}

@BindingAdapter("setProgressStep")
fun setProgressBarStep(stepProgressBar: StepProgressBar, status: String) {
    val step = when (status) {
        "상품인수" -> 1
        "상품이동중" -> 2
        "배송출발" -> 3
        "배달완료" -> 4
        else -> null
    }

    step?.let { stepProgressBar.step = it }
        ?: stepProgressBar.apply {
            stepUndoneColor = stepProgressBar.context.resources.getColor(R.color.yellow)
        }
}

@BindingAdapter("setProductName")
fun setProductName(view: TextView, name: String?) {
    var productText = "물품명 : "
    productText += if (name.isNullOrEmpty()) "-" else name
    view.text = productText
}