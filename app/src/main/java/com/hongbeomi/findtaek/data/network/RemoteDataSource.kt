package com.hongbeomi.findtaek.data.network

import com.hongbeomi.findtaek.models.dto.DeliveryResponse

/**
 * @author hongbeomi
 */

interface RemoteDataSource {

    suspend fun getData(carrierId: String?, trackId: String): DeliveryResponse

}