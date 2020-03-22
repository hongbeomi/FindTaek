package com.hongbeomi.findtaek.view.ui.main

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import co.mobiwise.materialintro.shape.ShapeType
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.databinding.ItemMainBinding
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.view.ui.main.MainRecyclerAdapter.Status.*
import com.hongbeomi.findtaek.view.util.IntroUtil.Companion.initIntro

/**
 * @author hongbeomi
 */

class MainRecyclerAdapter(
    private val activity: Activity,
    private val itemClick: (Delivery) -> Unit
) : RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder>() {

    private var itemList: List<Delivery> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int) =
        DataBindingUtil.inflate<ItemMainBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_main,
            parent,
            false
        ).let { ViewHolder(it) }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        if (itemList.isNotEmpty()) {
            initIntro(
                view = viewHolder.itemView,
                activity = activity,
                text = activity.getString(R.string.main_intro_delivery_item),
                key = MainActivity.KEY_INTRO_DELIVERY_ITEM,
                shapeType = ShapeType.RECTANGLE
            )
        }
        return viewHolder.bind(itemList[position])
    }

    fun setItemList(itemList: List<Delivery>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    fun getItemPosition(position: Int): Delivery = itemList[position]

    inner class ViewHolder(private val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val viewForeground = binding.constraintLayoutForeground
        fun bind(delivery: Delivery) {
            binding.delivery = delivery
            binding.status = when (delivery.status) {
                "상품인수" -> TAKEOVER
                "상품이동중" -> MOVING
                "배송출발" -> START
                "배달완료" -> COMPLETE
                else -> UNKNOWN
            }
            binding.root.setOnClickListener { itemClick(delivery) }
        }
    }

    enum class Status(@DrawableRes val drawableId: Int) {
        TAKEOVER(R.drawable.ic_product_takeover),
        MOVING(R.drawable.ic_product_moving),
        START(R.drawable.ic_start_delivery),
        COMPLETE(R.drawable.ic_delivery_complete),
        UNKNOWN(R.drawable.ic_unknown)
    }

}