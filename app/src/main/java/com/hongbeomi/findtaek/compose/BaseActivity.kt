package com.hongbeomi.findtaek.compose

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    private val lm by lazy { LinearLayoutManager(this) }

    fun initRecyclerView(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = lm
    }

}