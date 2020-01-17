package com.android.academy.services

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class HardWorker(context:Context, workerParams:WorkerParameters):
    Worker(context.applicationContext,workerParams){
    override fun doWork(): Result {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}