package com.android.academy.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.android.academy.R
import kotlinx.android.synthetic.main.activity_work_manager.*
import java.util.*

class WorkManagerActivity:AppCompatActivity() {
    private var workId:UUID? = null
    private val workerBackGroundProgressReceiver = WorkerBackGroundProgressReceiver()
    companion object{
        const val PROGRESS_UPDATE_ACTION = "PROGRESS_UPDATE_ACTION"
        const val PROGRESS_VALUE_KEY = "PROGRESS_VALUE_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_manager)
        wma_enqueue_work_button.setOnClickListener {
            enqueueWork()
        }
        wma_cancel_work_button.setOnClickListener {
            cancelWork()
        }
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(workerBackGroundProgressReceiver, IntentFilter(PROGRESS_UPDATE_ACTION))
    }

    override fun onStop() {
        unregisterReceiver(workerBackGroundProgressReceiver)
        super.onStop()
    }

    private fun enqueueWork(){
        val constraints =
            if(wma_network_switch.isChecked){
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .setRequiresCharging(wma_charging_switch.isChecked)
                    .setRequiresBatteryNotLow(wma_required_battery_switch.isChecked)
                    .build()
            }else
            {
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                    .setRequiresCharging(wma_charging_switch.isChecked)
                    .setRequiresBatteryNotLow(wma_required_battery_switch.isChecked)
                    .build()
            }
        val myOneTimeWorkRequest = OneTimeWorkRequest
            .Builder(HardWorker::class.java)
            .setConstraints(constraints)
            .build()
        workId = myOneTimeWorkRequest.id
        WorkManager.getInstance(this).enqueue(myOneTimeWorkRequest)
    }
    private fun cancelWork(){
        workId?.let {
            WorkManager.getInstance(this).cancelWorkById(it)
            workId = null
1        }
    }
    inner class WorkerBackGroundProgressReceiver: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent) {
            val progress = intent.getIntExtra(PROGRESS_VALUE_KEY,-1)
            if(progress>=0){
                val text:String = if(progress>99){
                    "Done!"
                }else{
                    String.format(Locale.getDefault(),"%d%%",progress)
                }
                wma_progress_percentages.text = text
            }
        }
    }
}