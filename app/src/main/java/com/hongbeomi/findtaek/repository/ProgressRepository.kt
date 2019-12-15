package com.hongbeomi.findtaek.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.hongbeomi.findtaek.api.ApiResponse
import com.hongbeomi.findtaek.api.client.DeliveryClient
//import com.hongbeomi.findtaek.api.client.ProgressClient
import com.hongbeomi.findtaek.api.message
import com.hongbeomi.findtaek.models.entity.ProgressesModel
import com.hongbeomi.findtaek.models.network.DeliveryResponse
//import com.hongbeomi.findtaek.models.network.ProgressResponse
import com.hongbeomi.findtaek.repository.util.Mapper

/**
 * @author hongbeomi
 */

class ProgressRepository
constructor(private val client: DeliveryClient) :
    Mapper<DeliveryResponse.Progresses, ProgressesModel> {

    private var progressesModelList: ArrayList<ProgressesModel> = arrayListOf()

    fun loadProgressList(
        carrierId: String, trackId: String, error: (String) -> Unit
    ): MutableLiveData<ArrayList<ProgressesModel>> {
        val mutableLiveData = MutableLiveData<ArrayList<ProgressesModel>>()
        client.fetchDelivery(carrierId, trackId) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    progressesModelList.clear()
                    response.data?.progresses?.forEach {
                        progressesModelList.add(mapFrom(it))
                    }
                    mutableLiveData.postValue(progressesModelList)
                }
                is ApiResponse.Failure.Error -> error(response.errorMessage)
                is ApiResponse.Failure.Exception -> {
                    error("통신 상태를 확인해주세요!")
                    Log.e("LoadProgress ERROR", response.message())
                }
            }
        }
        return mutableLiveData
    }

    override fun mapFrom(by: DeliveryResponse.Progresses): ProgressesModel =
        ProgressesModel(
            by.time,
            by.status.text,
            by.location.name,
            by.description
        )

}