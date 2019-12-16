package com.hongbeomi.findtaek.repository

import androidx.lifecycle.MutableLiveData
import com.hongbeomi.findtaek.api.ApiResponse
import com.hongbeomi.findtaek.api.client.DeliveryClient
import com.hongbeomi.findtaek.models.entity.TimeLine
import com.hongbeomi.findtaek.models.network.DeliveryResponse
import com.hongbeomi.findtaek.repository.util.Mapper

/**
 * @author hongbeomi
 */

class ProgressRepository
constructor(private val client: DeliveryClient) :
    Mapper<DeliveryResponse.Progresses, TimeLine> {

    private var timeLineList: ArrayList<TimeLine> = arrayListOf()

    fun loadProgressList(
        carrierId: String,
        trackId: String,
        error: (String) -> Unit
    ): MutableLiveData<ArrayList<TimeLine>> {
        val mutableLiveData = MutableLiveData<ArrayList<TimeLine>>()

        client.fetchDelivery(carrierId, trackId) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    timeLineList.clear()
                    response.data?.progresses?.forEach {
                        timeLineList.add(mapFrom(it))
                    }
                    mutableLiveData.postValue(timeLineList)
                }
                is ApiResponse.Failure.Error -> error(response.errorMessage)
                is ApiResponse.Failure.Exception -> error("통신 상태를 확인해주세요!")
            }
        }
        return mutableLiveData
    }

    override fun mapFrom(by: DeliveryResponse.Progresses): TimeLine =
        TimeLine(
            by.time,
            by.status.text,
            by.location.name,
            by.description
        )

}