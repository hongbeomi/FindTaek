package com.hongbeomi.findtaek.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.databinding.ItemMainBinding
import com.hongbeomi.findtaek.models.entity.Delivery

/**
 * @author hongbeomi
 */

class MainRecyclerAdapter(
    val ItemClick: (Delivery) -> Unit
) : RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>() {

    private var itemList: List<Delivery> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int) : MainViewHolder {
        return DataBindingUtil.inflate<ItemMainBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_main,
            parent,
            false
        ).let { MainViewHolder(it) }
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(mainViewHolder: MainViewHolder, position: Int) =
        mainViewHolder.bind(itemList[position])

    fun setItemList(itemList: List<Delivery>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    fun getItemPosition(position: Int): Delivery = itemList[position]

    inner class MainViewHolder(private val binding: ItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val viewForeground = binding.linearLayoutForeground
        fun bind(delivery: Delivery) {
            binding.delivery = delivery
            binding.root.setOnClickListener { ItemClick(delivery) }
        }
    }

}