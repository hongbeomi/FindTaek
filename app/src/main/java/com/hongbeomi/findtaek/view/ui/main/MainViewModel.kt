package com.hongbeomi.findtaek.view.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.compose.BaseViewModel
import com.hongbeomi.findtaek.repository.DeliveryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author hongbeomi
 */
class MainViewModel
constructor(private val repository: DeliveryRepository) :
    BaseViewModel() {

    lateinit var items: LiveData<List<Delivery>>

    fun getAll(): LiveData<List<Delivery>> {
        items = repository.getAll()
        return items
    }

    fun delete(delivery: Delivery) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(delivery)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun rollback(delivery: Delivery) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.rollback(delivery)
        }
    }

    fun update() {
        viewModelScope.launch(Dispatchers.IO) {
            if (isItemsValues()) {
                showToast("리스트가 비었습니다.")
            } else {
                for (item in items.value!!) {
                    repository.update(item) {
                        showToast(it)
                    }
                }
            }
        }
    }

    fun isItemsValues() = items.value.isNullOrEmpty()

}

