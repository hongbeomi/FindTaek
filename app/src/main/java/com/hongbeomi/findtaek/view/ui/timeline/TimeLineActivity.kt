package com.hongbeomi.findtaek.view.ui.timeline

import android.os.Bundle
import androidx.lifecycle.Observer
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.compose.BaseActivity
import com.hongbeomi.findtaek.models.entity.Progress
import com.hongbeomi.findtaek.view.adapter.TimeLineAdapter
import kotlinx.android.synthetic.main.activity_timeline.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class TimeLineActivity: BaseActivity() {

    private lateinit var vm: TimeLineViewModel
    private lateinit var adapter: TimeLineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
        setSupportActionBar(toolbar_timeline)

        vm = getViewModel()
        adapter = TimeLineAdapter()

        initRecyclerView(timeline_recyclerView, adapter)

        vm.getProgressesFromServer(intent.getStringExtra(TRACK_ID), intent.getStringExtra(CARRIER_ID))
            .observe(this, Observer<ArrayList<Progress>> { progress ->
            adapter.setProgressListItem(progress)
        })
    }

    companion object {
        const val TRACK_ID = "TRACK_ID"
        const val CARRIER_ID = "CARRIER_ID"
    }
}

