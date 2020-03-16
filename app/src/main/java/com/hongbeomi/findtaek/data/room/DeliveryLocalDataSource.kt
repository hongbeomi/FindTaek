package com.hongbeomi.findtaek.data_source

import com.hongbeomi.findtaek.db.DeliveryDao
import com.hongbeomi.findtaek.models.entity.Delivery

/**
 * @author hongbeomi
 */


class DeliveryLocalDataSource(private val deliveryDao: DeliveryDao) : LocalDataSource {

    override fun getAll() = deliveryDao.getAll()

    override suspend fun insert(delivery: Delivery) = deliveryDao.insert(delivery)

    override suspend fun update(delivery: Delivery)= deliveryDao.update(delivery)

    override suspend fun rollback(delivery: Delivery) = deliveryDao.insert(delivery)

    override suspend fun delete(delivery: Delivery) = deliveryDao.delete(delivery)

}