package com.android.academy.services

import android.app.IntentService
import android.content.Intent
import android.os.SystemClock

class HardJobIntentService:IntentService(HardJobIntentService::class.simpleName) {
    private var isDestroyed =false
    override fun onHandleIntent(intent: Intent?) {
        isDestroyed=false
        var i=0
        while (i<=100&&!isDestroyed){
            SystemClock.sleep(100)
            val broadcastIntent = Intent(BGServiceActivity.PROGRESS_UPDATE_ACTION)
            broadcastIntent.putExtra(BGServiceActivity.PROGRESS_VALUE_KEY,i)
            sendBroadcast(broadcastIntent)
            i++
        }
    }

    override fun onDestroy() {
        isDestroyed =true
        super.onDestroy()
    }
}