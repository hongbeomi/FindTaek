package com.hongbeomi.findtaek.view.ui.main

import android.app.Activity
import android.content.Intent
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
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
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
        const val REQUEST_CODE_UPDATE = 200
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
        setUpdateManager()
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
        showSnackBar(
            binding.root, this, getString(R.string.main_delete_complete), Snackbar.LENGTH_LONG,
            getString(R.string.main_snackbar_cancel)
        ) {
            viewModel.rollback(deletedItem)
        }
    }

    private fun setUpdateManager() {
        val appUpdateManager = AppUpdateManagerFactory.create(this)
        appUpdateManager?.let {
            it.appUpdateInfo.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
                ) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.FLEXIBLE,
                        this,
                        REQUEST_CODE_UPDATE
                    )
                }
            }
        }
        val listener = InstallStateUpdatedListener {
            if (it.installStatus() == InstallStatus.DOWNLOADED) {
                showSnackBar(
                    binding.root,
                    this,
                    getString(R.string.main_update_complete_title_text),
                    Snackbar.LENGTH_INDEFINITE,
                    getString(R.string.main_update_complete_cancel_text)
                ) {
                    appUpdateManager.completeUpdate()
                }
            }
        }
        appUpdateManager.registerListener(listener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_UPDATE) {
            if (resultCode != Activity.RESULT_OK) {
                showShort(getString(R.string.main_update_cancel_toast))
            }
        }
    }

}