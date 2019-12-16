package com.hongbeomi.findtaek.view.ui.timeline

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hongbeomi.findtaek.core.BaseViewModel
import com.hongbeomi.findtaek.models.entity.TimeLine
import com.hongbeomi.findtaek.repository.ProgressRepository
import kotlinx.coroutines.launch

/**
 * @author hongbeomi
 */

class TimeLineViewModel(private val repository: ProgressRepository) : BaseViewModel() {

    private var mutableLiveProgress = MutableLiveData<ArrayList<TimeLine>>()

    fun getAllTimeLine(carrierId: String, trackId: String): MutableLiveData<ArrayList<TimeLine>> {
        viewModelScope.launch {
            mutableLiveProgress = repository.loadProgressData(carrierId, trackId) {
                showToast(it)
            }
        }
        return mutableLiveProgress
    }

}
