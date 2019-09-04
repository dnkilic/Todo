package com.dnkilic.todo.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.dnkilic.todo.R
import com.dnkilic.todo.dashboard.DashboardActivity
import com.dnkilic.todo.notification.createNotification
import java.util.*

class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val title = intent.extras?.getString(EXTRA_TITLE) ?: GENERIC_TITLE
        val description = intent.extras?.getString(EXTRA_DESCRIPTION) ?: GENERIC_DESCRIPTION
        val id = intent.extras?.getLong(EXTRA_ID)!!

        val pendingIntent = PendingIntent.getActivity(context, 0, Intent(context, DashboardActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }, 0)

        createNotification(context, title, description, id.toInt(), pendingIntent)
    }

    companion object {

        private const val EXTRA_ID = "EXTRA_ID"
        private const val EXTRA_TITLE = "EXTRA_TITLE"
        private const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"

        private const val GENERIC_TITLE = "Todo Reminder!"
        private const val GENERIC_DESCRIPTION = "Don't forget to complete your task before the deadline."

        private fun createPendingIntent(context: Context,
                                        id: Long,
                                        title: String,
                                        description: String): PendingIntent? {
            val intent = Intent(context.applicationContext, AlarmBroadcastReceiver::class.java).apply {
                action = context.getString(R.string.action_notify_task_due_date)
                type = id.toString()
                putExtra("EXTRA_ID", id)
                putExtra("EXTRA_TITLE", title)
                putExtra("EXTRA_DESCRIPTION", description)
            }

            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        private fun createPendingIntent(context: Context,
                                        id: Long): PendingIntent? {
            val intent = Intent(context.applicationContext, AlarmBroadcastReceiver::class.java).apply {
                action = context.getString(R.string.action_notify_task_due_date)
                type = id.toString()
            }

            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        fun setAlarm(context: Context, id: Long, title: String, description: String, dueDate: Long) {
            val timeCal = Calendar.getInstance().apply {
                timeInMillis = dueDate
                set(Calendar.HOUR_OF_DAY, 12)
                set(Calendar.MINUTE, 0)
            }

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val pendingIntent = createPendingIntent(context, id, title, description)
            alarmManager.set(AlarmManager.RTC_WAKEUP, timeCal.timeInMillis, pendingIntent)
        }

        fun cancelAlarm(context: Context, id: Long) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val pendingIntent = createPendingIntent(context, id)
            alarmManager.cancel(pendingIntent)
        }
    }
}
