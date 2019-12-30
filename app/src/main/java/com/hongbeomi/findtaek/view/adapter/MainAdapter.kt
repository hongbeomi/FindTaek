package com.hongbeomi.findtaek.view.adapter

import android.annotation.SuppressLint
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

    private var itemList: List<Delivery> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int) = MainViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_main,
            parent,
            false
        )
    )

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(mainViewHolder: MainViewHolder, position: Int) =
        mainViewHolder.bind(itemList[position])

    fun setItemList(itemList: List<Delivery>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    fun getItemPosition(position: Int): Delivery = itemList[position]

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        LayoutContainer {

        override val containerView: View?
            get() = itemView
        val viewForeground: LinearLayout = foreground_view
        @SuppressLint("SetTextI18n")
        fun bind(delivery: Delivery) {
            delivery.apply {
                carrier_name_text.text = carrierName
                from_name_text.text = "From : $fromName"
                to_name_text.text = "To : $toName"
                status_text.text = status
                product_name_text.text = productName
                track_num_text.text = trackId
            }
            containerView?.setOnClickListener {
                ItemClick(delivery)
            }
        }

    }

}