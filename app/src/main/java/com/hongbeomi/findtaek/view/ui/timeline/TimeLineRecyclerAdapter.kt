package com.hongbeomi.findtaek.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.databinding.ItemMainBinding
import com.hongbeomi.findtaek.databinding.ItemTimelineBinding
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.view.util.VectorDrawableUtils
import com.hongbeomi.findtaek.models.entity.Progress
import com.hongbeomi.findtaek.models.network.DeliveryResponse
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_timeline.*

/**
 * @author hongbeomi
 */

class TimeLineRecyclerAdapter :
    RecyclerView.Adapter<TimeLineRecyclerAdapter.TimeLineViewHolder>() {

    private var itemList: ArrayList<DeliveryResponse.Progresses> = arrayListOf()

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder {
//        if (!::layoutInflater.isInitialized) {
//            layoutInflater = LayoutInflater.from(parent.context)
//        }

        return DataBindingUtil.inflate<ItemTimelineBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_timeline,
            parent,
            false
        ).let { TimeLineViewHolder(it, viewType) }

//        return TimeLineViewHolder(
//            layoutInflater.inflate(R.layout.item_timeline, parent, false),
//            viewType
//        )
    }

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {
//        setMarker(holder)
        holder.bind(itemList[position])
    }

    override fun getItemCount() = itemList.size

    fun setItemList(itemList: ArrayList<DeliveryResponse.Progresses>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

//    private fun setMarker(holder: TimeLineViewHolder) {
//
//        holder.timeline_view.marker =
//            VectorDrawableUtils.getDrawable(
//                holder.itemView.context,
//                R.drawable.ic_marker,
//                ContextCompat.getColor(holder.itemView.context, R.color.marine)
//            )
//    }

    inner class TimeLineViewHolder(private val binding: ItemTimelineBinding, viewType: Int) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.timelineView.initLine(viewType)
        }

        fun bind(progresses: DeliveryResponse.Progresses) {
            binding.progress = progresses
            binding.timelineView.marker =
                VectorDrawableUtils.getDrawable(
                    binding.root.context,
                    R.drawable.ic_marker,
                    ContextCompat.getColor(binding.root.context, R.color.marine)
                )
        }
    }
//    inner class (itemView: View, viewType: Int) :
//        RecyclerView.ViewHolder(itemView), LayoutContainer {
//
//        init { timeline_view.initLine(viewType) }
//
//        override val containerView: View?
//            get() = itemView
//        fun bind(progress: Progress) {
//            progress.apply {
//                progress_time_text.text = time
//                progress_description_text.text = description
//                progress_location_name_text.text = locationName
//                progress_status_text.text = statusText
//            }
//        }
//    }

}