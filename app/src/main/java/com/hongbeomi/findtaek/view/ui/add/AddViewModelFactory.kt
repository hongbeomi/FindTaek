package com.hongbeomi.findtaek.view.ui.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hongbeomi.findtaek.repository.DeliveryRepository

class AddViewModelFactory(private val repository: DeliveryRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AddViewModel(repository) as T
    }

}