package com.hongbeomi.findtaek.view.ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
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

    open val bottomSheetTopMargin by lazy { resources.getDimensionPixelSize(R.dimen.bottom_sheet_top_margin) }

    protected inline fun <reified T : ViewDataBinding> binding(
        inflater: LayoutInflater,
        @LayoutRes resId: Int,
        container: ViewGroup?,
    ): T = DataBindingUtil.inflate(inflater, resId, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
            .apply {
                setOnShowListener {
                    var fullScreenHeight = window?.decorView!!.run {
                        height - rootWindowInsets.run { systemWindowInsetTop + systemWindowInsetBottom }
                    }
                    fullScreenHeight -= bottomSheetTopMargin
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

}