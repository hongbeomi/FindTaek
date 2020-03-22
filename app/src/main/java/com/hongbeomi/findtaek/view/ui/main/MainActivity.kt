package com.hongbeomi.findtaek.view.ui.main

import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.google.android.material.snackbar.Snackbar
import com.hongbeomi.findtaek.data.worker.DeliveryWorker
import com.hongbeomi.findtaek.databinding.ActivityMainBinding
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.view.ui.add.AddDialogFragment
import com.hongbeomi.findtaek.view.ui.base.BaseActivity
import com.hongbeomi.findtaek.view.ui.timeline.TimeLineDialogFragment
import com.hongbeomi.findtaek.view.util.KEY_WORK_DATA
import com.hongbeomi.findtaek.view.util.ToastUtil.Companion.showShort
import com.hongbeomi.findtaek.view.util.serializeToJson
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import com.hongbeomi.findtaek.R
import java.util.concurrent.TimeUnit

/**
 * @author hongbeomi
 */

class MainActivity : BaseActivity(), MainRecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private val binding by binding<ActivityMainBinding>(R.layout.activity_main)
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MainRecyclerAdapter
    private val workManager = WorkManager.getInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbarMain)
        viewModel = getViewModel()
        binding.apply {
            lifecycleOwner = this@MainActivity
            vm = viewModel
        }
        initObserver()
        setRecyclerView()
        setSwipeRefresh()
    }

    private fun setRecyclerView() {
        adapter = MainRecyclerAdapter { delivery ->
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
                binding.recyclerViewMain.scheduleLayoutAnimation()
            }
            isRefresh.observe(::getLifecycle) {
                it?.let {
                    if (it) showShort(R.string.main_complete_list_update)
                    else showShort(R.string.main_list_empty)
                    binding.swipeLayout.isRefreshing = false
                }
            }
            onSendCoordinatesEvent.observe(::getLifecycle) {
                AddDialogFragment().show(supportFragmentManager, null)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        startWork(viewModel.allDeliveryList.value)
    }

    private fun startWork(deliveryList: List<Delivery>?) {
        val deliveryWork = PeriodicWorkRequest.Builder(
            DeliveryWorker::class.java, 1, TimeUnit.HOURS
        ).setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).setInputData(
            workDataOf(Pair(KEY_WORK_DATA, serializeToJson(deliveryList)))
        ).build()

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
            .make(coordinator, getString(R.string.main_delete_complete), Snackbar.LENGTH_LONG)
            .setTextColor(ContextCompat.getColor(this, R.color.marine))
            .setAction(getString(R.string.main_snackbar_cancel)) { viewModel.rollback(deleteItem) }
            .setActionTextColor(Color.WHITE)
            .also {
                it.view.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.snackBarColor)
                )
            }
            .show()
    }

}

