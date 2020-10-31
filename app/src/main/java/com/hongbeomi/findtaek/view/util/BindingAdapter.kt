package com.hongbeomi.findtaek.view.util

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.daimajia.swipe.SwipeLayout
import kr.co.prnd.StepProgressBar

/**
 * @author hongbeomi
 */

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
        else -> 0
    }
    stepProgressBar.step = step
}

@BindingAdapter("setProductName")
fun setProductName(view: TextView, name: String?) {
    var productText = "물품명 : "
    productText += if (name.isNullOrEmpty()) "-" else name
    view.text = productText
}