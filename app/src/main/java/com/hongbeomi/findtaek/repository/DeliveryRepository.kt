package com.hongbeomi.findtaek.repository

import androidx.lifecycle.LiveData
import com.hongbeomi.findtaek.api.ApiResponse
import com.hongbeomi.findtaek.api.client.DeliveryClient
import com.hongbeomi.findtaek.db.DeliveryDao
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.models.network.DeliveryResponse
import com.hongbeomi.findtaek.repository.util.*
import com.hongbeomi.findtaek.view.util.CarrierIdMap
import com.hongbeomi.findtaek.view.util.event.hideLoading
import com.hongbeomi.findtaek.view.ui.add.AddViewModel.Companion.finishEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author hongbeomi
 */

class DeliveryRepository
constructor(
    private val deliveryDao: DeliveryDao,
    private val deliveryClient: DeliveryClient,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : Mapper<DeliveryResponse, Delivery> {

    private lateinit var inputProductName: String
    private lateinit var inputCarrierId: String
    private lateinit var inputTrackId: String
    var id: Long? = null

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

    suspend fun loadDeliveryDataAndInsert(
        productName: String,
        carrierName: String,
        trackId: String,
        error: (String) -> Unit
    ) = withContext(ioDispatcher) {
        val carrierId = CarrierIdMap().convertId(carrierName)
        deliveryClient.fetchDelivery(carrierId, trackId) { response ->
            when (response) {
                is ApiResponse.Success -> {
                    response.data?.let {
                        inputProductName = productName
                        inputCarrierId = carrierId
                        inputTrackId = trackId
                        deliveryDao.insert(mapFrom(response.data))
                        finishEvent.value = true
                    }
                }
                is ApiResponse.Failure.Error -> {
                    when (carrierId) {
                        "null" -> error(response.errorMessage)
                        else -> error("해당 번호에 대한 배송정보가 없습니다.")
                    }
                }
                is ApiResponse.Failure.Exception -> {
                    error("통신 상태를 확인해주세요!")
                }
            }
            hideLoading()
        }
    }

    suspend fun loadDeliveryDataAndUpdate(delivery: Delivery, error: (String) -> Unit) =
        withContext(ioDispatcher) {
            deliveryClient.fetchDelivery(delivery.carrierId, delivery.trackId) { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        response.data?.let { data ->
                            deliveryDao.update(makeUpdateDelivery(delivery, data))
                        }
                        error("업데이트 완료!")
                    }
                    is ApiResponse.Failure.Error -> error(response.errorMessage)
                    is ApiResponse.Failure.Exception -> error("통신 상태를 확인해주세요!")
                }
            }
        }

    suspend fun rollbackByRepository(delivery: Delivery) = withContext(ioDispatcher) {
        deliveryDao.insert(delivery)
    }

    suspend fun deleteByRepository(delivery: Delivery) = withContext(ioDispatcher) {
        deliveryDao.delete(delivery)
    }

    fun getAllByRepository(): LiveData<List<Delivery>> =
        deliveryDao.getAll()

}
