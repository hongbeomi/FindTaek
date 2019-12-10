package com.hongbeomi.findtaek.view.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.compose.BaseActivity
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.util.RecyclerItemTouchHelper
import com.hongbeomi.findtaek.extension.sendFabButtonLocation
import com.hongbeomi.findtaek.extension.swipeRefreshRecyclerView
import com.hongbeomi.findtaek.util.TimeLineActivity.CARRIER_ID
import com.hongbeomi.findtaek.util.TimeLineActivity.TRACK_ID
import com.hongbeomi.findtaek.view.adapter.MainAdapter
import com.hongbeomi.findtaek.view.ui.timeline.TimeLineActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * @author hongbeomi
 */
class MainActivity : BaseActivity(), RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private lateinit var adapter: MainAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mainViewModel = getViewModel()

        adapter = MainAdapter { delivery ->
            startActivity<TimeLineActivity>(
                TRACK_ID to delivery.trackId,
                CARRIER_ID to delivery.carrierId
            )
        }

        initRecyclerView(recyclerView, adapter)

        mainViewModel.also {
            it.observeToast(this) { message -> toast(message) }
            it.getAll().observe(this, Observer<List<Delivery>> { deliveryList ->
                adapter.setItems(deliveryList)
            })
        }

        RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this).let {
            ItemTouchHelper(it).attachToRecyclerView(recyclerView)
        }

        swipeRefreshRecyclerView(swipeView, mainViewModel)

        fab.setOnClickListener { v -> sendFabButtonLocation(v) }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        val deletedItem = adapter.getDeliveryPosition(position)
        mainViewModel.delete(adapter.getDeliveryPosition(position))

        Snackbar.make(coordinator, "물품 삭제완료", Snackbar.LENGTH_LONG)
            .setAction("취소") {
                GlobalScope.launch(Dispatchers.IO) {
                    mainViewModel.rollback(deletedItem)
                }
            }
            .setActionTextColor(Color.YELLOW)
            .show()
    }

}

