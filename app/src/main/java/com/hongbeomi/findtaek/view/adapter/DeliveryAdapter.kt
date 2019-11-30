package com.hongbeomi.findtaek.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.models.entity.Delivery
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_main.*


class DeliveryAdapter(val ItemClick: (Delivery) -> Unit)
    : RecyclerView.Adapter<DeliveryAdapter.ViewHolder>() {

    private var deliveryList: List<Delivery> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_main,
            parent,
            false
        )
    )

    override fun getItemCount() = deliveryList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(deliveryList[position])

    // android KTX + LayoutContainer 를 사용해서 findViewId() 캐시 사용
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {

        override val containerView: View?
            get() = itemView
        val viewForeground: LinearLayout = view_foreground
        fun bind(delivery: Delivery) {
            delivery.apply {
                carrier_name.text = carrierName
                from_name.text = fromName
                from_time.text = fromTime
                to_name.text = toName
                to_time.text = toTime
                status_textView.text = status
                product_name.text = productName
                product_num.text = trackId
            }
            containerView?.setOnClickListener {
                ItemClick(delivery)
            }
        }

    }

    fun setDeliveryList(deliveryList: List<Delivery>) {
        this.deliveryList = deliveryList
        notifyDataSetChanged()
    }

    fun getDeliveryPosition(position: Int): Delivery = this.deliveryList[position]

}