package com.hongbeomi.findtaek.view.ui.main

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
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
    private val itemClick: (Delivery) -> Unit,
    private val itemDelete: (Delivery) -> Unit
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

    inner class ViewHolder(private val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(delivery: Delivery) {
            binding.delivery = delivery
            binding.status = when (delivery.status) {
                "상품인수" -> TAKEOVER
                "상품이동중" -> MOVING
                "배송출발" -> START
                "배달완료" -> COMPLETE
                else -> UNKNOWN
            }

            binding.constraintLayoutForeground.setOnClickListener {
                binding.swipeLayoutItemMain.close()
                if (!binding.constraintLayoutDeleteButtonContainer.isShown) {
                    itemClick(delivery)
                }
            }
            binding.constraintLayoutDeleteButtonContainer.setOnClickListener {
                itemDelete(delivery)
            }
        }

    }

    enum class Status {
        TAKEOVER,
        MOVING,
        START,
        COMPLETE,
        UNKNOWN
    }

}