package com.ray.monsterhunter

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ray.monsterhunter.util.Logger
import com.ray.monsterhunter.util.UserManager

class WorkerManager(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    //建頻道
    lateinit var manager: NotificationManager
    lateinit var builder: Notification.Builder
    lateinit var channel: NotificationChannel
    private val channelId = "chanel101"
    private val description = "test Notification"

        override fun doWork(): Result {
            getchanel(UserManager.userData.id!!)
            return Result.success()
        }

    fun getchanel(userName : String) {
        manager = MonsterApplication.instance.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(MonsterApplication.instance, LauncherActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(MonsterApplication.instance, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel =
                NotificationChannel(channelId, "MyChannel", NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableLights(true)
            channel.lightColor = Color.GREEN
            channel.enableVibration(false)
            manager.createNotificationChannel(channel)
            builder = Notification.Builder(MonsterApplication.instance, channelId)
                .setSmallIcon(R.drawable.ic_note)
                .setContentTitle("守護你的約定")
                .setContentText("${userName} 快去創建房間與你的隊友一起屠龍吧！！")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
        } else {
            builder = Notification.Builder(MonsterApplication.instance)
                .setSmallIcon(R.drawable.ic_note)
                .setContentTitle("守護你的約定")
                .setContentText("${userName} 快去創建房間與你的隊友一起屠龍吧！！")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
        }
        manager.notify(1234, builder.build())


    }
    }