package com.hongbeomi.findtaek.data.room

import com.hongbeomi.findtaek.data.room.dao.DeliveryDao
import com.hongbeomi.findtaek.models.entity.Delivery

/**
 * @author hongbeomi
 */


class DeliveryLocalDataSource(private val deliveryDao: DeliveryDao) : LocalDataSource {

    override fun getAll() = deliveryDao.getAll()

    override suspend fun insert(delivery: Delivery) = deliveryDao.insert(delivery)

    override suspend fun updateAll(deliveryList: List<Delivery>)= deliveryDao.updateAll(deliveryList)

    override suspend fun rollback(delivery: Delivery) = deliveryDao.insert(delivery)

    override suspend fun delete(delivery: Delivery) = deliveryDao.delete(delivery)

}