package com.yoyo.facerecognition.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.yoyo.facerecognition.R


class NotificationHelper private constructor(private val context: Context) {
    companion object : SingletonHolder<NotificationHelper, Context>(::NotificationHelper) {
        private const val CHANNEL_ID = "100032"
        private const val CHANNEL_NAME = "summery Channel"
        private const val NOTIFICATION_ID = 100033
    }

    private val notificationManager = NotificationManagerCompat.from(context)

    fun showSummeryNotification(txt: String) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            createChannelId()
            val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentText(txt)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setChannelId(CHANNEL_ID)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
            notificationManager.notify(NOTIFICATION_ID, mBuilder.build())
        }else{
            val mBuilder = NotificationCompat.Builder(context)
                .setContentText(txt)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
            notificationManager.notify(NOTIFICATION_ID, mBuilder.build())
        }



    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannelId() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

    }

    fun cancelUpgradeNotification() {
        notificationManager.cancel(NOTIFICATION_ID)
    }
}