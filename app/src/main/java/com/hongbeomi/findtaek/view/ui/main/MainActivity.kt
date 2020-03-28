package com.hongbeomi.findtaek.view.ui.main

import android.graphics.Color
import android.os.Bundle
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import co.mobiwise.materialintro.shape.ShapeType
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.data.worker.DeliveryWorker
import com.hongbeomi.findtaek.databinding.ActivityMainBinding
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.view.ui.add.AddDialogFragment
import com.hongbeomi.findtaek.view.ui.base.BaseActivity
import com.hongbeomi.findtaek.view.ui.timeline.TimeLineDialogFragment
import com.hongbeomi.findtaek.view.util.IntroUtil.Companion.initIntro
import com.hongbeomi.findtaek.view.util.KEY_WORK_DATA
import com.hongbeomi.findtaek.view.util.ToastUtil.Companion.showShort
import com.hongbeomi.findtaek.view.util.serializeToJson
import org.jetbrains.anko.padding
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.concurrent.TimeUnit

/**
 * @author hongbeomi
 */

class MainActivity : BaseActivity(), MainRecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private val binding by binding<ActivityMainBinding>(R.layout.activity_main)
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainRecyclerAdapter
    private val workManager = WorkManager.getInstance(this)

    companion object {
        const val KEY_INTRO_ADD_BUTTON = "key_intro_add_button"
        const val KEY_INTRO_DELIVERY_ITEM = "key_intro_delivery_item"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbarMain)
        viewModel = getViewModel()

        initIntro(
            view = binding.floatingButtonMain,
            activity = this,
            text = getString(R.string.main_intro_add_button),
            key = KEY_INTRO_ADD_BUTTON,
            shapeType = ShapeType.CIRCLE
        )

        binding.apply {
            lifecycleOwner = this@MainActivity
            vm = viewModel
        }
        initObserver()
        setRecyclerView()
        setSwipeRefresh()
    }

    private fun setRecyclerView() {
        adapter = MainRecyclerAdapter(this) { delivery ->
            viewModel.getProgressesList(delivery.carrierName, delivery.trackId) {
                TimeLineDialogFragment.newInstance(it).show(supportFragmentManager, null)
            }
        }
        binding.recyclerViewMain.adapter = this.adapter
        MainRecyclerItemTouchHelper(0, ItemTouchHelper.RIGHT, this).let {
            ItemTouchHelper(it).attachToRecyclerView(binding.recyclerViewMain)
        }
    }

    private fun initObserver() {
        viewModel.apply {
            allDeliveryList.observe(::getLifecycle) {
                adapter.setItemList(it)
                callInitUpdate()
                binding.recyclerViewMain.scheduleLayoutAnimation()
            }
            isRefresh.observe(::getLifecycle) {
                it?.let {
                    if (it) showShort(R.string.main_complete_list_update)
                    else showShort(R.string.main_list_empty)
                    binding.swipeLayout.isRefreshing = false
                }
            }
            onInitUpdateEvent.observe(::getLifecycle) { updateAll() }
            onSendCoordinatesEvent.observe(::getLifecycle) {
                AddDialogFragment().show(supportFragmentManager, null)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.allDeliveryList.value.takeIf { !it.isNullOrEmpty() }?.let { startWork(it) }
    }

    private fun startWork(deliveryList: List<Delivery>?) {
        val deliveryWork = PeriodicWorkRequest
            .Builder(DeliveryWorker::class.java, 1, TimeUnit.HOURS)
            .setConstraints(
                Constraints
                    .Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .setInputData(workDataOf(Pair(KEY_WORK_DATA, serializeToJson(deliveryList))))
            .build()

        workManager.apply {
            cancelAllWork()
            enqueue(deliveryWork)
        }
    }

    private fun setSwipeRefresh() {
        binding.swipeLayout.apply {
            setColorSchemeResources(android.R.color.holo_blue_light)
            setOnRefreshListener { viewModel.updateAll() }
        }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int) {
        val deletedItem = adapter.getItemPosition(position)
        viewModel.delete(deletedItem)
        showSnackBar(deletedItem)
    }

    private fun showSnackBar(deleteItem: Delivery) {
        Snackbar
            .make(binding.root, getString(R.string.main_delete_complete), Snackbar.LENGTH_LONG)
            .setTextColor(ContextCompat.getColor(this, R.color.marine))
            .setAction(getString(R.string.main_snackbar_cancel)) { viewModel.rollback(deleteItem) }
            .setActionTextColor(Color.WHITE)
            .setBackgroundTint(resources.getColor(R.color.snackBarColor))
            .setGestureInsetBottomIgnored(true)
            .show()
    }

}