package com.hongbeomi.findtaek.data.work_manager

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.hongbeomi.findtaek.FindTaekApp
import com.hongbeomi.findtaek.data.repository.DeliveryRepository
import com.hongbeomi.findtaek.data.repository.Repository
import com.hongbeomi.findtaek.data.room.DeliveryLocalDataSource
import com.hongbeomi.findtaek.data.room.dao.DeliveryDao
import com.hongbeomi.findtaek.data.room.dao.DeliveryDatabase
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.view.util.deserializeFromJson
import com.hongbeomi.findtaek.view.util.sendNotification
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.koin.core.KoinComponent
import org.koin.core.get
import org.koin.core.inject
import java.util.*

class DeliveryWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    private val workQueue = LinkedList<Delivery>() as Queue<Delivery>

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val repository: Repository by inject()

        Log.e("시작함", "###############")

//        val data = repository.getAll().value

        val a = inputData.getString("data")
        val data: List<Delivery>? = deserializeFromJson(a)

        if (data.isNullOrEmpty()) {
            Log.e("무엇", data.toString())
            return@withContext Result.retry()
        }
        /**
         * 상태가 배달완료인 것은 빼고 큐에 모두 추가한다.
         */
        /**
         * 상태가 배달완료인 것은 빼고 큐에 모두 추가한다.
         */
        data.forEach { workQueue.offer(it) }
//            .filter { it.status != "배달완료" }

        sendNotification(context)

        return@withContext try {
            /**
             * 큐 반복 작업 시작!
             */
            /**
             * 큐 반복 작업 시작!
             */
            workQueue.forEach {
                /**
                 * 서버에서 최신 정보 받아옴
                 */
                /**
                 * 서버에서 최신 정보 받아옴
                 */
                val response = repository.getData(it.carrierName, it.trackId)

                /**
                 * 먼저 room에 업데이트
                 */

                /**
                 * 먼저 room에 업데이트
                 */
                repository.update(
                    response.toDelivery(it.carrierName, it.productName, it.trackId)
                )
                /**
                 * 서버에서 받아온 택배의 상태가 배송출발이면 알람 보내고 큐에서 삭제
                 */
                /**
                 * 서버에서 받아온 택배의 상태가 배송출발이면 알람 보내고 큐에서 삭제
                 */
                if (response.state.text == "배달완료") {
                    sendNotification(context)
//                    workQueue.remove(it)
                }
            }
            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount < 3) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }

}