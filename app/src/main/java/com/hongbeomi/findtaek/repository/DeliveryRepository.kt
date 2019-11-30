package com.hongbeomi.findtaek.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.hongbeomi.findtaek.api.ApiResponse
import com.hongbeomi.findtaek.api.client.DeliveryClient
import com.hongbeomi.findtaek.api.message
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.models.network.DeliveryResponse
import com.hongbeomi.findtaek.room.DeliveryDao
import com.hongbeomi.findtaek.util.Mapper
import com.hongbeomi.findtaek.util.carrierIdMap
import kotlinx.coroutines.*

class DeliveryRepository
constructor(
    private val deliveryDao: DeliveryDao,
    private val deliveryClient: DeliveryClient
) : Mapper<DeliveryResponse, Delivery> {

    private val deliveryList: LiveData<List<Delivery>> = deliveryDao.getAll()
    private lateinit var inputProductName: String
    private lateinit var inputCarrierId: String
    private lateinit var inputTrackId: String

    var id: Long? = null
    fun insert(product_name: String, carrier_name: String, track_Id: String, error: (String) -> Unit) {
            deliveryClient.fetchDelivery(findCarrierId(carrier_name), track_Id) { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        if (response.data?.progresses?.size!! > 0) {
                            inputProductName = product_name
                            inputTrackId = track_Id
                            inputCarrierId = findCarrierId(carrier_name)
                            GlobalScope.launch(Dispatchers.IO) {
                                deliveryDao.insertItem(mapFrom(response.data))
                            }
                        } else {
                            error("상품이 준비중이거나 존재하지 않습니다.")
                        }
                    }
                    is ApiResponse.Failure.Error -> error(response.message())
                    is ApiResponse.Failure.Exception -> {
                        error("통신 상태를 확인해주세요!")
                        Log.e("INSERT ERROR", response.message())
                    }
                }
            }
    }

    fun getAll(): LiveData<List<Delivery>> = deliveryList

    fun update(delivery: Delivery, error: (String) -> Unit) {
            deliveryClient.fetchDelivery(delivery.carrierId, delivery.trackId) { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        response.data?.let { data ->
                            GlobalScope.launch(Dispatchers.IO) {
                                deliveryDao.updateItem(updateConverter(delivery, data))
                            }
                        }
                    }
                    is ApiResponse.Failure.Error -> error(response.message())
                    is ApiResponse.Failure.Exception -> {
                        error("통신 상태를 확인해주세요!")
                        println(response.message())
                    }
                }
        }
    }

    private fun findCarrierId(inputCarrierName: String) =
        carrierIdMap[inputCarrierName].toString()

    fun rollback(delivery: Delivery) {
        GlobalScope.launch(Dispatchers.IO) {
            deliveryDao.insertItem(delivery)
        }
    }

    fun delete(delivery: Delivery) {
        GlobalScope.launch(Dispatchers.IO) {

            deliveryDao.deleteItem(delivery)
        }
    }

    fun deleteAll() {
        GlobalScope.launch(Dispatchers.IO) {
            deliveryDao.deleteAll()
        }
    }

    override fun mapFrom(by: DeliveryResponse) =
        Delivery(
            id = id,
            fromName = by.from.name,
            fromTime = by.from.time,
            toName = by.to.name,
            toTime = decideToTime(by.to.time),
            carrierId = inputCarrierId,
            carrierName = by.carrier.name,
            productName = inputProductName,
            trackId = inputTrackId,
            status = by.state.text
        )

    private fun updateConverter(delivery: Delivery, data: DeliveryResponse) =
            Delivery(
                id = delivery.id,
                fromName = data.from.name,
                fromTime = data.from.time,
                toName = data.to.name,
                toTime = decideToTime(data.to.time),
                carrierId = delivery.carrierId,
                carrierName = data.carrier.name,
                productName = delivery.productName,
                trackId = delivery.trackId,
                status = data.state.text
        )

    private fun decideToTime(toTimeData: String) =
        if(isNullOrEmptyToTime(toTimeData)) "정보없음" else toTimeData.substring(0, 10)

    private fun isNullOrEmptyToTime(toTimeData: String) = toTimeData.isNullOrEmpty()

}
