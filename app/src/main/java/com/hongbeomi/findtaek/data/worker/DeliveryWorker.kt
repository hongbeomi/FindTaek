package com.hongbeomi.findtaek.data.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hongbeomi.findtaek.data.repository.Repository
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.view.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * @author hongbeomi
 */

class DeliveryWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val repository: Repository by inject()

        Log.w("WorkManager 작업 시작", "########################################")

        val data: List<Delivery>? = deserializeFromJson(inputData.getString(KEY_WORK_DATA))

        if (data.isNullOrEmpty()) return@withContext Result.retry()

        return@withContext try {
            data.filter {
                it.status != STATE_DELIVERY_COMPLETE &&
                it.status != STATE_DELIVERY_START }
                .map {
                    val response = repository.getData(it.carrierName, it.trackId)
                    if (response.state.text == STATE_DELIVERY_START) {
                        sendNotification(context, it.carrierName, it.trackId)
                    }
                    response.toDelivery(it.id, it.carrierName, it.productName, it.trackId)
                }
                .apply { repository.updateAll(this) }
            Result.success()
        } catch (e: Exception) {
            runAttemptCount.takeIf { it < 3 }?.let { Result.retry() } ?: Result.failure()
        }
    }

}