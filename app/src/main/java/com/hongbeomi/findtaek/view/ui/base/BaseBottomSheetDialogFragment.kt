package com.hongbeomi.findtaek.view.ui.base

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hongbeomi.findtaek.R

/**
 * @author hongbeomi
 */

open class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    protected inline fun <reified T : ViewDataBinding> binding(
        inflater: LayoutInflater,
        @LayoutRes resId: Int,
        container: ViewGroup?
    ): T = DataBindingUtil.inflate(inflater, resId, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
            .apply {
                setOnShowListener {
                    var fullScreenHeight = 0
                    if (checkSDKVersion())
                        fullScreenHeight = window?.decorView!!.run {
                            height - rootWindowInsets.run { systemWindowInsetTop + systemWindowInsetBottom }
                        }
                    fullScreenHeight -= resources.getDimensionPixelSize(R.dimen.bottom_sheet_top_margin)
                    findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
                        .also {
                            BottomSheetBehavior.from(it).apply {
                                peekHeight = fullScreenHeight
                                setState(BottomSheetBehavior.STATE_EXPANDED)
                            }
                            it.layoutParams = it.layoutParams.apply { height = fullScreenHeight }
                        }
                }
            }
    }

    private fun checkSDKVersion() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M

}