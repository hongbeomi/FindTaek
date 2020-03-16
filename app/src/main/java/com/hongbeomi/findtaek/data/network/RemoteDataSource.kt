package com.hongbeomi.findtaek.data_source

import com.hongbeomi.findtaek.models.entity.DeliveryResponse

/**
 * @author hongbeomi
 */

interface RemoteDataSource {
    suspend fun getData(carrierId: String?, trackId: String): DeliveryResponse
}