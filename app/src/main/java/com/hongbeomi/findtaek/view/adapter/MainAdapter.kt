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

/**
 * @author hongbeomi
 */
class MainAdapter(val ItemClick: (Delivery) -> Unit) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var items: List<Delivery> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int) = MainViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_main,
            parent,
            false
        )
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(mainViewHolder: MainViewHolder, position: Int) {
        mainViewHolder.bind(items[position])
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {

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

    fun setItems(items: List<Delivery>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun getDeliveryPosition(position: Int): Delivery = items[position]

}