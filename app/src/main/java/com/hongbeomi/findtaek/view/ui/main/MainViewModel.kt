package com.hongbeomi.findtaek.view.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.compose.BaseViewModel
import com.hongbeomi.findtaek.repository.DeliveryRepository
import kotlinx.coroutines.launch

class MainViewModel
constructor(private val repository: DeliveryRepository) :
    BaseViewModel() {

    private val items = repository.getAll()

    fun getAll(): LiveData<List<Delivery>> {
        return repository.getAll()
    }

    fun delete(delivery: Delivery) {
        viewModelScope.launch {
            repository.delete(delivery)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

    fun rollback(delivery: Delivery) {
        viewModelScope.launch {
            repository.rollback(delivery)
        }
    }

    fun updateDeliveryFromServer() {
        viewModelScope.launch {
            for (item in items.value!!) {
                repository.update(item) {
                    showToast(it)
                }
            }
        }
    }
}

