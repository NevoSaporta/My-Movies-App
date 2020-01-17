package com.android.academy.services

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.*
import com.android.academy.R
import kotlinx.android.synthetic.main.activity_work_manager.*

class WorkManagerActivity:AppCompatActivity() {
    private var workRunnig =false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_manager)
    }
    companion object{
        const val TAG ="HARD_WORKER_REQUEST"
        const val VIEW ="TEXT_VIEW"
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
        val inputData = Data.Builder()
            .putInt(VIEW,R.id.wma_progress_percentages)
            .build()
        val myOneTimeWorkRequest = OneTimeWorkRequest
            .Builder(HardWorker::class.java)
            .setConstraints(constraints)
            .setInputData(inputData)
            .addTag(TAG)
            .build()
        WorkManager.getInstance(this).enqueue(myOneTimeWorkRequest)
        workRunnig =true
    }
    private fun cancelWork(){
        if(workRunnig){
            WorkManager.getInstance(this).cancelAllWorkByTag(TAG)
        }
        workRunnig =false
    }
}