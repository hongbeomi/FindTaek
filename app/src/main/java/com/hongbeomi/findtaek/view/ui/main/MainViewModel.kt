package com.hongbeomi.findtaek.view.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.core.BaseViewModel
import com.hongbeomi.findtaek.repository.DeliveryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author hongbeomi
 */

class MainViewModel
constructor(private val repository: DeliveryRepository) :
    BaseViewModel() {

    lateinit var liveItemList: LiveData<List<Delivery>>

    fun getAll(): LiveData<List<Delivery>> {
        liveItemList = repository.getAll()
        return liveItemList
    }

    fun delete(delivery: Delivery) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(delivery)
        }
    }

    fun rollback(delivery: Delivery) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.rollback(delivery)
        }
    }

    fun update() {
        try {
            for (item in liveItemList.value!!) {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.loadDeliveryDataAndUpdate(item) {
                        showToast(it)
                    }
                }
            }
        } catch (e: NullPointerException) {
            showToast("리스트가 비었습니다!")
        }
    }

}

