package com.android.academy.services

import android.content.Context
import android.content.Intent
import android.os.SystemClock
import androidx.work.Worker
import androidx.work.WorkerParameters

class HardWorker(context:Context, workerParams:WorkerParameters):
    Worker(context.applicationContext,workerParams){
    private var isWorkerStopped =false

    override fun doWork(): Result {
        isWorkerStopped =false
        var i=0
        while (i<=100&&!isWorkerStopped){
            SystemClock.sleep(100)
            val broadcastIntent = Intent(WorkManagerActivity.PROGRESS_UPDATE_ACTION)
            broadcastIntent.putExtra(WorkManagerActivity.PROGRESS_VALUE_KEY,i)
            applicationContext.sendBroadcast(broadcastIntent)
            i++
        }
        return if (i<100){
            Result.failure()
        }else{
            Result.success()
        }


    }

    override fun onStopped() {
        isWorkerStopped =true
        super.onStopped()
    }
}