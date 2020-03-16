package com.hongbeomi.findtaek.data_source


/**
 * @author hongbeomi
 */


class DeliveryRemoteDataSource(
    private val deliveryService: DeliveryService
): RemoteDataSource {

    override suspend fun getData(carrierId: String?, trackId: String) =
         deliveryService.getData(carrierId ?: "null", trackId)

}