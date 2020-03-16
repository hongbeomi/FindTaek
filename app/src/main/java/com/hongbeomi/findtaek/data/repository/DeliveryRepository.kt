package com.hongbeomi.findtaek.repository

import com.hongbeomi.findtaek.data.room.LocalDataSource
import com.hongbeomi.findtaek.data.network.RemoteDataSource
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.models.dto.DeliveryResponse
import com.hongbeomi.findtaek.view.util.CarrierIdUtil

/**
 * @author hongbeomi
 */

class DeliveryRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : Repository {

    override suspend fun getData(carrierName: String, trackId: String) =
        remoteDataSource.getData(CarrierIdUtil().convertId(carrierName), trackId)

    override fun getAll() = localDataSource.getAll()

    override suspend fun insert(
        trackId: String,
        carrierName: String,
        productName: String,
        deliveryResponse: DeliveryResponse
    ) = localDataSource.insert(
        deliveryResponse.toDelivery(carrierName, productName, trackId)
    )

    override suspend fun update(delivery: Delivery) = localDataSource.update(delivery)

    override suspend fun rollback(delivery: Delivery) = localDataSource.rollback(delivery)

    override suspend fun delete(delivery: Delivery) = localDataSource.delete(delivery)

}
