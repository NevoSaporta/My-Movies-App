package com.android.academy.services

import android.app.Service
import android.content.Intent
import android.os.*

class HardJobService:Service() {

    private var isDestroyed =false
    private lateinit var servicedLooper :Looper
    private lateinit var serviceHandler:ServiceHandler

    override fun onCreate() {
        super.onCreate()
        val thread =HandlerThread(HardJobService::class.simpleName,Process.THREAD_PRIORITY_BACKGROUND)
        thread.start()

        servicedLooper = thread.looper
        serviceHandler = ServiceHandler(servicedLooper)

    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isDestroyed =false

        val message = serviceHandler.obtainMessage()
        message.arg1 =startId
        serviceHandler.sendMessage(message)
        return START_STICKY
    }

    inner class ServiceHandler(looper:Looper):Handler(looper){
        override fun handleMessage(msg: Message) {
            var i=0
            while(i<=100&&!isDestroyed){
                SystemClock.sleep(100)
                val intent = Intent(BGServiceActivity.PROGRESS_UPDATE_ACTION)
                intent.putExtra(BGServiceActivity.PROGRESS_VALUE_KEY,i)
                sendBroadcast(intent)
                i++
            }
            stopSelf(msg.arg1)
        }
    }

    override fun onDestroy() {
        isDestroyed=true
        super.onDestroy()
    }
}
