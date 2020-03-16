package com.hongbeomi.findtaek.view.base

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author hongbeomi
 */

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    protected inline fun <reified T : ViewDataBinding> binding(resId: Int): Lazy<T> =
        lazy { DataBindingUtil.setContentView<T>(this, resId) }

    private val linearLayoutManager by lazy { LinearLayoutManager(this) }

    fun initRecyclerView(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>)=
        recyclerView.apply {
            setHasFixedSize(true)
            this.adapter = adapter
            layoutManager = linearLayoutManager
        }

}