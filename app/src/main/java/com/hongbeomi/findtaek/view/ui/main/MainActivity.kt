package com.hongbeomi.findtaek.view.ui.main

import android.graphics.*
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.compose.BaseActivity
import com.hongbeomi.findtaek.models.entity.Delivery
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
import com.hongbeomi.findtaek.util.RecyclerItemTouchHelper
import com.hongbeomi.findtaek.view.adapter.DeliveryAdapter
import com.hongbeomi.findtaek.view.ui.add.AddActivity
import com.hongbeomi.findtaek.view.ui.timeline.TimeLineActivity
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : BaseActivity(), RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private val lm by lazy { LinearLayoutManager(this) }
    private lateinit var mainVM: MainViewModel
    private lateinit var adapter: DeliveryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mainVM = getViewModel()
        adapter = DeliveryAdapter { delivery ->
                startActivity<TimeLineActivity>(
                    TimeLineActivity.TRACK_ID to delivery.trackId,
                    TimeLineActivity.CARRIER_ID to delivery.carrierId
                )
            }

//        recyclerView.apply {
//            setHasFixedSize(true)
//        }.also {
//            it.adapter = adapter
//            it.layoutManager = lm
//        }
        initRecyclerView(recyclerView, adapter)

        mainVM.also {
            it.observeToast(this) { message -> toast(message) }
            it.getAll().observe(this, Observer<List<Delivery>> { deliveryList ->
                adapter.setDeliveryList(deliveryList)
            })
        }

        RecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this).let {
            ItemTouchHelper(it).attachToRecyclerView(recyclerView)
        }

        fab.setOnClickListener {
            startActivity<AddActivity>()
        }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        val deletedItem = adapter.getDeliveryPosition(position)
        mainVM.delete(adapter.getDeliveryPosition(position))

        Snackbar
            .make(coordinator, "물품 삭제완료", Snackbar.LENGTH_LONG)
            .setAction("취소") {
                mainVM.insert(deletedItem)
            }
            .setActionTextColor(Color.YELLOW)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_menu, menu)
        val rotation: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.rotation)

        (menu?.findItem(R.id.menuRefresh)?.actionView as ImageView)
            .apply {
                setImageResource(R.drawable.ic_autorenew_white_48dp)
                scaleX = 0.5f
                scaleY = 0.5f
                setOnClickListener {
                    it.startAnimation(rotation)
                    mainVM.updateDeliveryFromServer()
                    lm.scrollToPosition(0)
                }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menuDeleteAll) {
            mainVM.deleteAll()
        }
        return super.onOptionsItemSelected(item)
    }

}

