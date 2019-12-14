package com.hongbeomi.findtaek.repository

import androidx.lifecycle.LiveData
import com.hongbeomi.findtaek.api.ApiResponse
import com.hongbeomi.findtaek.api.client.DeliveryClient
import com.hongbeomi.findtaek.db.DeliveryDao
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.models.network.DeliveryResponse
import com.hongbeomi.findtaek.repository.util.*

/**
 * @author hongbeomi
 */

class DeliveryRepository
constructor(
    private val deliveryDao: DeliveryDao,
    private val deliveryClient: DeliveryClient
) : Mapper<DeliveryResponse, Delivery> {

    private lateinit var inputProductName: String
    private lateinit var inputCarrierId: String
    private lateinit var inputTrackId: String
    var id: Long? = null

    fun insert(
        product_name: String, carrier_name: String, track_Id: String,
        error: (String) -> Unit
    ) {
        deliveryClient.fetchDelivery(convertCarrierId(carrier_name), track_Id) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.let {
                        inputProductName = product_name
                        inputTrackId = track_Id
                        inputCarrierId = convertCarrierId(carrier_name)
                        deliveryDao.insertItem(mapFrom(response.data))
                        finishActivity()
                    }
                }
                is ApiResponse.Failure.Error -> {
                    error(response.errorMessage)
                }
                is ApiResponse.Failure.Exception -> {
                    error("통신 상태를 확인해주세요!")
                }
            }
        }
    }

    fun update(delivery: Delivery, error: (String) -> Unit) {
        deliveryClient.fetchDelivery(delivery.carrierId, delivery.trackId) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.let { data ->
                        deliveryDao.updateItem(makeUpdateDelivery(delivery, data))
                    }
                    error("업데이트 완료!")
                }
                is ApiResponse.Failure.Error -> {
                    error(response.errorMessage)
                }
                is ApiResponse.Failure.Exception -> {
                    error("통신 상태를 확인해주세요!")
                }
            }
        }
    }

    fun rollback(delivery: Delivery) = deliveryDao.insertItem(delivery)

    fun delete(delivery: Delivery) = deliveryDao.deleteItem(delivery)

    fun getAll(): LiveData<List<Delivery>> = deliveryDao.getAll()

    override fun mapFrom(by: DeliveryResponse) =
        Delivery(
            id = id,
            fromName = by.from.name,
            fromTime = by.from.time,
            toName = by.to.name,
            toTime = determineTime(by.to.time),
            carrierId = inputCarrierId,
            carrierName = by.carrier.name,
            productName = inputProductName,
            trackId = inputTrackId,
            status = by.state.text
        )

}
