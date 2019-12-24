package com.hongbeomi.findtaek.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.view.util.VectorDrawableUtils
import com.hongbeomi.findtaek.models.entity.Progress
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_timeline.*

/**
 * @author hongbeomi
 */

class TimeLineAdapter :
    RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder>() {

    private lateinit var layoutInflater: LayoutInflater
    private var items: ArrayList<Progress> = arrayListOf()

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder {
        if (!::layoutInflater.isInitialized) {
            layoutInflater = LayoutInflater.from(parent.context)
        }

        return TimeLineViewHolder(
            layoutInflater.inflate(R.layout.item_timeline, parent, false),
            viewType
        )
    }

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {
        setMarker(holder)
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun setItems(items: ArrayList<Progress>){
        this.items = items
        notifyDataSetChanged()
    }

    private fun setMarker(holder: TimeLineViewHolder) {
        holder.timeline.marker =
            VectorDrawableUtils.getDrawable(
                holder.itemView.context,
                R.drawable.ic_marker,
                ContextCompat.getColor(holder.itemView.context, android.R.color.holo_blue_light)
            )
    }

    inner class TimeLineViewHolder(itemView: View, viewType: Int) :
        RecyclerView.ViewHolder(itemView), LayoutContainer {

        init { timeline.initLine(viewType) }

        override val containerView: View?
            get() = itemView
        fun bind(progress: Progress) {
            progress.apply {
                progress_time.text = time
                progress_description.text = description
                progress_location_name.text = locationName
                progress_status_text.text = statusText
            }
        }
    }

}