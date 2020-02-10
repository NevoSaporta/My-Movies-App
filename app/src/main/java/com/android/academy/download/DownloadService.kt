package com.android.academy.download

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.os.IBinder

class DownloadService:Service() {

    companion object {
        const val URL: String = "URL"

        fun startService(activity: Activity, url: String) {
            val intent = Intent(activity, DownloadService::class.java)
            intent.putExtra(URL, url)
            activity.startService(intent)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }
}