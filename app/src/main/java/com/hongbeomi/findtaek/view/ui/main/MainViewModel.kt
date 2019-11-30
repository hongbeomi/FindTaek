package com.hongbeomi.findtaek.view.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.compose.BaseViewModel
import com.hongbeomi.findtaek.extension.server
import com.hongbeomi.findtaek.extension.with
import com.hongbeomi.findtaek.repository.DeliveryRepository

class MainViewModel
constructor(private val repository: DeliveryRepository) : BaseViewModel() {

    private val items = repository.getAll()

    fun getAll(): LiveData<List<Delivery>> = repository.getAll()
    fun delete(delivery: Delivery) = repository.delete(delivery)
    fun deleteAll() = repository.deleteAll()

    private fun update(delivery: Delivery) = repository.update(delivery)
    fun insert(delivery: Delivery) = repository.insert(delivery)

    fun updateDeliveryFromServer() {
        for (delivery in items.value!!) {
            addToDisposable(
                server.getList(trackId = delivery.trackId, carrierId = delivery.carrierId).with()
//                    .doOnSubscribe {
//                        showProgress()
//                    }
//                    .doOnTerminate {
//                        hideProgress()
//                    }
                .subscribe({
                    it.run {
                        update(delivery = delivery)
                    }
                }) {
                    Log.e("MainViewModel Error", it.message)
                    showToast("통신 상태를 확인해주세요...")
                })
        }
    }

}
