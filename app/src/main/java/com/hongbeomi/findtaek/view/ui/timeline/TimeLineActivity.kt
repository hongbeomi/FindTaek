package com.hongbeomi.findtaek.view.ui.timeline

import android.os.Bundle
import androidx.lifecycle.Observer
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.core.BaseActivity
import com.hongbeomi.findtaek.models.entity.ProgressesModel
import com.hongbeomi.findtaek.util.TimeLineActivity.CARRIER_ID
import com.hongbeomi.findtaek.util.TimeLineActivity.TRACK_ID
import com.hongbeomi.findtaek.view.adapter.TimeLineAdapter
import kotlinx.android.synthetic.main.activity_timeline.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * @author hongbeomi
 */
class TimeLineActivity: BaseActivity() {

    private lateinit var timeLineViewModel: TimeLineViewModel
    private lateinit var adapter: TimeLineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
        setSupportActionBar(toolbar_timeline)

        timeLineViewModel = getViewModel()
        adapter = TimeLineAdapter()

        initRecyclerView(timeline_recyclerView, adapter)

        timeLineViewModel.getProgress(
            intent.getStringExtra(CARRIER_ID),
            intent.getStringExtra(TRACK_ID)
        ).observe(this, Observer<ArrayList<ProgressesModel>> { progress ->
            adapter.setItems(progress)
        })
    }

}

