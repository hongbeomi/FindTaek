package com.hongbeomi.findtaek.repository

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.hongbeomi.findtaek.api.ApiResponse
import com.hongbeomi.findtaek.api.client.DeliveryClient
import com.hongbeomi.findtaek.models.entity.Progress
import com.hongbeomi.findtaek.models.network.DeliveryResponse
import com.hongbeomi.findtaek.repository.util.Mapper
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

/**
 * @author hongbeomi
 */

class ProgressRepository
constructor(private val client: DeliveryClient) :
    Mapper<DeliveryResponse.Progresses, Progress> {

    private var progressList: ArrayList<Progress> = arrayListOf()

    override fun mapFrom(by: DeliveryResponse.Progresses): Progress =
        Progress(
            getFormatterTime(by.time),
            by.status.text,
            by.location.name,
            by.description
        )

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

    @SuppressLint("SimpleDateFormat")
    private fun getFormatterTime(time: String): String {
        return SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분")
            .format(
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(time)
            )
    }

}