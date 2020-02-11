package com.android.academy.download

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.nfc.Tag
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.academy.R

class DownloadService:Service() {

    companion object {
        const val URL: String = "URL"
        const val ONGOING_NOTIFICATION_ID: Int = 14000605
        const val BROADCAST_ACTION: String = "com.academy.fundamentals.DOWNLOAD_COMPLETE"

        val TAG = DownloadService::class.java.simpleName

        private const val CHANNEL_DEFAULT_IMPORTANCE = "Channel"

        fun startService(activity: Activity, url: String) {
            val intent = Intent(activity, DownloadService::class.java)
            intent.putExtra(URL, url)
            activity.startService(intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        startForeground()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val url = intent.getStringExtra(URL)
        url?.let {
            DownloadThread(it,object :DownloadThread.DownloadCallBack{
                override fun onProgressUpdate(percent: Int) {
                    updateNotification(percent)
                }

                override fun onDownloadFinished(filePath: String) {
                    sendBroadcastMsgDownloadComplete(filePath)
                    stopSelf()
                }

                override fun onError(error: String) {
                    Log.e(TAG, "DownloadService, DownloadThread, Error: $error")
                    stopSelf()
                }

            }).start()
        }
        return START_STICKY
    }


    private fun sendBroadcastMsgDownloadComplete(filePath: String) {
        val intent = Intent(BROADCAST_ACTION)
        intent.putExtra(DownloadActivity.ARG_FILE_PATH, filePath)
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private fun updateNotification(progress: Int) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(ONGOING_NOTIFICATION_ID, createNotification(progress))
    }


    private fun startForeground() {
        createNotificationChannel()
        startForeground(ONGOING_NOTIFICATION_ID, createNotification(0))
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //SK
            val name = getString(R.string.channel_name) // The user-visible name of the channel.
            val description = getString(R.string.channel_description) // The user-visible description of the channel.
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_DEFAULT_IMPORTANCE, name, importance)
            mChannel.description = description // Configure the notification channel.
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

    private fun createNotification(progress: Int): Notification {
        val notificationIntent = Intent(this, DownloadActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
        val progressStr = getString(R.string.notification_message, progress)

        return NotificationCompat.Builder(this, CHANNEL_DEFAULT_IMPORTANCE)
            .setContentTitle(getText(R.string.notification_title))
            .setContentText(progressStr)
            .setSmallIcon(android.R.drawable.stat_sys_download )
            .setProgress(100, progress, false)
            .setContentIntent(pendingIntent)
            .build()
    }

}