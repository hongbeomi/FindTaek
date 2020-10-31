package com.hongbeomi.findtaek.view.ui.main

import android.os.Bundle
import androidx.work.*
import co.mobiwise.materialintro.shape.ShapeType
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
import com.hongbeomi.findtaek.view.util.SnackBarUtil.Companion.showSnackBar
import com.hongbeomi.findtaek.view.util.ToastUtil.Companion.showShort
import com.hongbeomi.findtaek.view.util.observeOnce
import com.hongbeomi.findtaek.view.util.serializeToJson
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.concurrent.TimeUnit

/**
 * @author hongbeomi
 */

class MainActivity : BaseActivity() {

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

    override fun onDestroy() {
        super.onDestroy()
        viewModel.allDeliveryList.value.takeIf { !it.isNullOrEmpty() }?.let { startWork(it) }
    }

    private fun setRecyclerView() {
        adapter = MainRecyclerAdapter(this, { delivery ->
            viewModel.getProgressesList(delivery.carrierName, delivery.trackId) {
                TimeLineDialogFragment.newInstance(it).show(supportFragmentManager, null)
            }
        }, { delivery -> viewModel.delete(delivery)
            showSnackBar(
                binding.root, this, getString(R.string.main_delete_complete), Snackbar.LENGTH_LONG,
                getString(R.string.main_snackbar_cancel)
            ) { viewModel.rollback(delivery) }
        })
        binding.recyclerViewMain.adapter = this.adapter
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
            onInitUpdateEvent.observeOnce(this@MainActivity) { updateAll() }
            onSendCoordinatesEvent.observe(::getLifecycle) {
                AddDialogFragment().show(supportFragmentManager, null)
            }
        }
    }

    private fun startWork(deliveryList: List<Delivery>?) {
        workManager.cancelAllWork()
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
        workManager.enqueue(deliveryWork)
    }

    private fun setSwipeRefresh() {
        binding.swipeLayout.apply {
            setColorSchemeResources(android.R.color.holo_blue_light)
            setOnRefreshListener { viewModel.updateAll() }
        }
    }

}