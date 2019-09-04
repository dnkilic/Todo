package com.dnkilic.todo.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

private fun createNotificationChannel(context: Context,
                              importance: Int,
                              showBadge: Boolean,
                              name: String,
                              description: String): String? {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = "${context.packageName}-$name"
        val channel = NotificationChannel(channelId, name, importance)
            .also {
                it.description = description
                it.setShowBadge(showBadge)
            }

        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        return channelId
    }

    return null
}

fun createNotification(context: Context,
                       title: String,
                       description: String,
                       id: Int,
                       pendingIntent: PendingIntent,
                       importance: Int? = null,
                       showBadge: Boolean = true) {
    val channelId = createNotificationChannel(context,
        importance ?: NotificationManager.IMPORTANCE_DEFAULT, showBadge, title, description)
    val notificationBuilder = if (channelId != null) {
        NotificationCompat.Builder(context, channelId)
    } else {
        NotificationCompat.Builder(context)
    }.apply {
        setSmallIcon(R.drawable.ic_notifications)
        setContentTitle(title)
        setContentText(description)
        priority = NotificationCompat.PRIORITY_DEFAULT
        setContentIntent(pendingIntent)
    }

    val notificationManager = NotificationManagerCompat.from(context)
    notificationManager.notify(id, notificationBuilder.build())
}