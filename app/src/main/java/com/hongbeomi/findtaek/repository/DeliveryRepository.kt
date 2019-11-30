package com.hongbeomi.findtaek.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.room.DeliveryDatabase
import com.hongbeomi.findtaek.room.DeliveryDao
import com.hongbeomi.findtaek.util.ioThread

class DeliveryRepository
    constructor(private val deliveryDao: DeliveryDao) {

//    private val deliveryDatabase = DeliveryDatabase.getInstance(application)
//    private val deliveryDao: DeliveryDao = deliveryDatabase.deliveryDao()
    private val deliveryList: LiveData<List<Delivery>> = deliveryDao.getAll()

    fun getAll(): LiveData<List<Delivery>> = deliveryList

    fun insert(delivery: Delivery) {
        ioThread {
            deliveryDao.insertItem(delivery)
        }
    }

    fun delete(delivery: Delivery) {
        ioThread {
            deliveryDao.deleteItem(delivery)
        }
    }

    fun deleteAll() {
        ioThread {
            deliveryDao.deleteAll()
        }
    }

    fun update(delivery: Delivery) {
        ioThread {
            deliveryDao.updateItem(delivery)
        }
    }

}