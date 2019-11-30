package com.hongbeomi.findtaek.view.ui.timeline

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hongbeomi.findtaek.models.entity.Progress
import com.hongbeomi.findtaek.compose.BaseViewModel
import com.hongbeomi.findtaek.repository.ProgressRepository
import kotlinx.coroutines.launch

class TimeLineViewModel(private val repository: ProgressRepository) : BaseViewModel() {

//    private var progressList: ArrayList<Progress> = arrayListOf()
    private var mutableLiveProgress = MutableLiveData<ArrayList<Progress>>()

//    fun getProgressesFromServer(trackId: String, carrierId: String): MutableLiveData<ArrayList<Progress>> {
//        addToDisposable(
//            service.getList(trackId = trackId, carrierId = carrierId)
//            .with()
////                    .doOnSubscribe {
////                        showProgress()
////                    }
////                    .doOnTerminate {
////                        hideProgress()
////                    }
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                it.run {
//                    progresses.forEach { progress ->
//                        progressList.add(mapFrom(progress))
//                    }
//                    mutableLiveProgress += progressList
//                }
//            }) {
//                Log.e("MainViewModel Error", it.message)
//                showToast("통신 상태를 확인해주세요...")
//            })
//        return mutableLiveProgress
//    }

    fun getProgress(carrierId: String, trackId: String): MutableLiveData<ArrayList<Progress>> {
        viewModelScope.launch {
            mutableLiveProgress = repository.getProgressFromServer(carrierId, trackId) {
                showToast(it)
            }
        }
        return mutableLiveProgress
    }

}
