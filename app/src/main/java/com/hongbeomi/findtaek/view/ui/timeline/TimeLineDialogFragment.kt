package com.hongbeomi.findtaek.view.ui.timeline

import android.content.Context
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.databinding.ActivityTimelineBinding
import com.hongbeomi.findtaek.databinding.DialogTimelineBinding
import com.hongbeomi.findtaek.models.entity.DeliveryResponse
import com.hongbeomi.findtaek.view.ui.main.MainViewModel

class TimeLineDialog(
    context: Context
//    private val viewModel: MainViewModel,
//    private val carrierId: String,
//    private val trackId: String
//    private val list: ArrayList<DeliveryResponse.Progresses>
) : BottomSheetDialog(context) {

    val binding = DialogTimelineBinding.inflate(
        layoutInflater,
        layoutInflater.inflate(R.layout.dialog_timeline, null) as ViewGroup,
        false
    )
    lateinit var adapter: TimeLineRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val binding = DialogTimelineBinding.inflate(
//            layoutInflater,
//            layoutInflater.inflate(R.layout.dialog_timeline, null) as ViewGroup,
//            false
//        )
        setContentView(binding.root)
        adapter = TimeLineRecyclerAdapter()

//        viewModel.getProgressesList(carrierId, trackId)
//        adapter.setItemList(list)
//        binding = DataBindingUtil.bind(R.layout.dialog_timeline)
        binding.timelineDialogRecyclerView.adapter = adapter
    }

}