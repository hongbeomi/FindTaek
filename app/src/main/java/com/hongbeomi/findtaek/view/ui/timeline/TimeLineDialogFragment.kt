package com.hongbeomi.findtaek.view.ui.timeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.databinding.FragmentTimelineBinding
import com.hongbeomi.findtaek.models.dto.DeliveryResponse
import com.hongbeomi.findtaek.view.ui.base.BaseBottomSheetDialogFragment

/**
 * @author hongbeomi
 */


class TimeLineDialogFragment : BaseBottomSheetDialogFragment() {

    companion object {
        const val PROGRESSES = "progresses"
        fun newInstance(
            progresses: ArrayList<DeliveryResponse.Progresses>
        ) = TimeLineDialogFragment().apply {
            arguments = Bundle().apply { putParcelableArrayList(PROGRESSES, progresses) }
        }
    }

    private lateinit var adapter: TimeLineRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TimeLineRecyclerAdapter()
        adapter.setItemList(arguments?.getParcelableArrayList(PROGRESSES)!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = DataBindingUtil.inflate<FragmentTimelineBinding>(
        inflater,
        R.layout.fragment_timeline, container, false
    ).apply {
        lifecycleOwner = viewLifecycleOwner
        this.timelineDialogRecyclerView.adapter = adapter
    }.root

}