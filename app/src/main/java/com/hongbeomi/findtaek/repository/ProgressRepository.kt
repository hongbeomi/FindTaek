package com.hongbeomi.findtaek.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hongbeomi.findtaek.api.ApiResponse
import com.hongbeomi.findtaek.api.client.ProgressClient
import com.hongbeomi.findtaek.api.message
import com.hongbeomi.findtaek.models.entity.Progress
import com.hongbeomi.findtaek.models.network.ProgressResponse
import com.hongbeomi.findtaek.repository.util.Mapper

/**
 * @author hongbeomi
 */
class ProgressRepository
constructor(private val client: ProgressClient) :
    Mapper<ProgressResponse.Progresses, Progress> {

    private var progressList: ArrayList<Progress> = arrayListOf()

    fun loadProgressList(carrierId: String, trackId: String, error : (String) -> Unit
    ): MutableLiveData<ArrayList<Progress>> {
        val mutableLiveProgress = MutableLiveData<ArrayList<Progress>>()
        client.fetchDelivery(carrierId, trackId) { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        progressList.clear()
                        response.data?.progresses?.forEach {
                            progressList.add(mapFrom(it))
                        }
                        mutableLiveProgress.postValue(progressList)
                    }
                    is ApiResponse.Failure.Error -> error(response.message())
                    is ApiResponse.Failure.Exception -> {
                        error("통신 상태를 확인해주세요!")
                        Log.e("Progress ERROR", response.message())
                    }
                }
            }
        return mutableLiveProgress
    }

    override fun mapFrom(by: ProgressResponse.Progresses): Progress =
        Progress(
            by.time,
            by.status.text,
            by.location.name,
            by.description
        )

}