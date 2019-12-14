package com.hongbeomi.findtaek.view.ui.timeline

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hongbeomi.findtaek.core.BaseViewModel
import com.hongbeomi.findtaek.models.entity.ProgressesModel
import com.hongbeomi.findtaek.repository.ProgressRepository
import kotlinx.coroutines.launch

/**
 * @author hongbeomi
 */

class TimeLineViewModel(private val repository: ProgressRepository) : BaseViewModel() {

    private var mutableLiveProgress = MutableLiveData<ArrayList<ProgressesModel>>()

    fun getProgress(carrierId: String, trackId: String): MutableLiveData<ArrayList<ProgressesModel>> {
        viewModelScope.launch {
            mutableLiveProgress = repository.loadProgressList(carrierId, trackId) {
                showToast(it)
            }
        }
        return mutableLiveProgress
    }

}
