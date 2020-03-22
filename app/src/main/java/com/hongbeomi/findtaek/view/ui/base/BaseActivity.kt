package com.hongbeomi.findtaek.view.ui.base

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @author hongbeomi
 */

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    protected inline fun <reified VB : ViewDataBinding> binding(resId: Int): Lazy<VB> =
        lazy { DataBindingUtil.setContentView<VB>(this, resId) }

}