package com.hongbeomi.findtaek.view.ui.timeline

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hongbeomi.findtaek.models.entity.Progress
import com.hongbeomi.findtaek.models.network.DeliveryResponse
import com.hongbeomi.findtaek.compose.BaseViewModel
import com.hongbeomi.findtaek.extension.server
import com.hongbeomi.findtaek.extension.plusAssign
import com.hongbeomi.findtaek.extension.with
import com.hongbeomi.findtaek.util.Mapper
import io.reactivex.android.schedulers.AndroidSchedulers

class TimeLineViewModel : BaseViewModel(), Mapper<DeliveryResponse.Progresses, Progress>{

    private var progressList: ArrayList<Progress> = arrayListOf()
    private var mutableLiveProgress = MutableLiveData<ArrayList<Progress>>()

    fun getProgressesFromServer(trackId: String, carrierId: String): MutableLiveData<ArrayList<Progress>> {
        addToDisposable(
            server.getList(trackId = trackId, carrierId = carrierId)
            .with()
//                    .doOnSubscribe {
//                        showProgress()
//                    }
//                    .doOnTerminate {
//                        hideProgress()
//                    }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                it.run {
                    progresses.forEach { progress ->
                        progressList.add(mapFrom(progress))
                    }
                    mutableLiveProgress += progressList
                }
            }) {
                Log.e("MainViewModel Error", it.message)
                showToast("통신 상태를 확인해주세요...")
            })
        return mutableLiveProgress
    }


    override fun mapFrom(by: DeliveryResponse.Progresses) =
        Progress(
            by.time,
            by.status.text,
            by.location.name,
            by.description
        )

}
