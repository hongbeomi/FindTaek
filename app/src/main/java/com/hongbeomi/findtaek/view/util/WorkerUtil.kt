package com.hongbeomi.findtaek.view.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.gson.Gson
import com.hongbeomi.findtaek.R
import com.hongbeomi.findtaek.models.entity.Delivery
import com.hongbeomi.findtaek.view.ui.splash.SplashActivity


const val STATE_DELIVERY_COMPLETE = "배달완료"
const val STATE_DELIVERY_START = "배송출발"
const val KEY_WORK_DATA = "work_data"
const val NOTIFICATION_CHANNEL = "notification_channel"
const val NOTIFICATION_NAME = "notification_name"
const val NOTIFICATION_ID = 1

fun serializeToJson(deliveryList: List<Delivery>?): String? =
    Gson().toJson(deliveryList)

fun deserializeFromJson(jsonString: String?): List<Delivery>? =
    Gson().fromJson(jsonString, Array<Delivery>::class.java).toList()

fun sendNotification(context: Context, carrierName: String, trackId: String) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    val contentIntent = PendingIntent.getActivity(
        context, 0,
        Intent(context, SplashActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT
    )

    val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .setSmallIcon(R.drawable.splash_findtaek)
        .setLargeIcon(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.ic_app_notification
            )
        )
        .setStyle(androidx.media.app.NotificationCompat.MediaStyle())
        .setContentTitle(context.getString(R.string.view_notification_content_title))
        .setContentText("$carrierName - 운송장 번호 : $trackId")
        .setDefaults(Notification.DEFAULT_ALL)
        .setAutoCancel(true)
        .setContentIntent(contentIntent)
        .apply { priority = NotificationCompat.PRIORITY_MAX }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        notification.setChannelId(NOTIFICATION_CHANNEL)

        val ringtoneManager =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL,
            NOTIFICATION_NAME, NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableLights(true)
            lightColor = Color.BLUE
            enableVibration(true)
            vibrationPattern = longArrayOf(100, 300, 100, 300)
            setSound(ringtoneManager, audioAttributes)
        }

        notificationManager.createNotificationChannel(channel)
    }
    notificationManager.notify(NOTIFICATION_ID, notification.build())
}