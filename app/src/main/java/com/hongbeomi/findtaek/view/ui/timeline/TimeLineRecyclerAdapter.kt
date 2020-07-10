package com.hongbeomi.findtaek.view.ui.timeline

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.databinding.ItemTimelineBinding
import com.hongbeomi.findtaek.models.dto.DeliveryResponse.Progresses

/**
 * @author hongbeomi
 */

class TimeLineRecyclerAdapter : RecyclerView.Adapter<TimeLineRecyclerAdapter.TimeLineViewHolder>() {

    private var itemList: ArrayList<Progresses> = arrayListOf()

    override fun getItemViewType(position: Int) =
        TimelineView.getTimeLineViewType(position, itemCount)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataBindingUtil.inflate<ItemTimelineBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_timeline,
            parent,
            false
        ).let { TimeLineViewHolder(it, viewType) }

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) =
        holder.bind(itemList[itemList.size.minus(1) - position])

    override fun getItemCount() = itemList.size

    fun setItemList(itemList: ArrayList<Progresses>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    inner class TimeLineViewHolder(private val binding: ItemTimelineBinding, viewType: Int) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.timelineViewLine.initLine(viewType)
        }

        fun bind(progresses: Progresses) {
            binding.progress = progresses
        }
    }

}