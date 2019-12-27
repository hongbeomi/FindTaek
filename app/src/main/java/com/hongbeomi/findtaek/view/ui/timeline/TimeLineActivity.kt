package com.hongbeomi.findtaek.view.ui.timeline

import android.os.Bundle
import androidx.lifecycle.Observer
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.core.BaseActivity
import com.hongbeomi.findtaek.models.entity.Progress
import com.hongbeomi.findtaek.view.util.TimeLineActivity.EXTRA_CARRIER_ID
import com.hongbeomi.findtaek.view.util.TimeLineActivity.EXTRA_TRACK_ID
import com.hongbeomi.findtaek.view.adapter.TimeLineAdapter
import kotlinx.android.synthetic.main.activity_timeline.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * @author hongbeomi
 */

class TimeLineActivity : BaseActivity() {

    private lateinit var timeLineViewModel: TimeLineViewModel
    private lateinit var adapter: TimeLineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline)
        setSupportActionBar(timeline_toolbar)

        timeLineViewModel = getViewModel()
        adapter = TimeLineAdapter()

        initRecyclerView(timeline_recycler_view, adapter)

        timeLineViewModel.getAllProgress(
            intent.getStringExtra(EXTRA_CARRIER_ID),
            intent.getStringExtra(EXTRA_TRACK_ID)
        ).observe(this, Observer<ArrayList<Progress>> { timeline ->
            adapter.setItems(timeline)
        })
    }

}

