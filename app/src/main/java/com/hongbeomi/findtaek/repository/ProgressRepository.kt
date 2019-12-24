package com.hongbeomi.findtaek.repository

import androidx.lifecycle.MutableLiveData
import com.hongbeomi.findtaek.api.ApiResponse
import com.hongbeomi.findtaek.api.client.DeliveryClient
import com.hongbeomi.findtaek.models.entity.Progress
import com.hongbeomi.findtaek.models.network.DeliveryResponse
import com.hongbeomi.findtaek.repository.util.Mapper

/**
 * @author hongbeomi
 */

class ProgressRepository
constructor(private val client: DeliveryClient) :
    Mapper<DeliveryResponse.Progresses, Progress> {

    private var progressList: ArrayList<Progress> = arrayListOf()

    fun loadProgressData(
        carrierId: String,
        trackId: String,
        error: (String) -> Unit
    ): MutableLiveData<ArrayList<Progress>> {
        val mutableLiveData = MutableLiveData<ArrayList<Progress>>()

        client.fetchDelivery(carrierId, trackId) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    progressList.clear()
                    response.data?.progresses?.forEach {
                        progressList.add(mapFrom(it))
                    }
                    mutableLiveData.postValue(progressList)
                }
                is ApiResponse.Failure.Error -> error(response.errorMessage)
                is ApiResponse.Failure.Exception -> error("통신 상태를 확인해주세요!")
            }
        }
        return mutableLiveData
    }

    override fun mapFrom(by: DeliveryResponse.Progresses): Progress =
        Progress(
            by.time,
            by.status.text,
            by.location.name,
            by.description
        )

}