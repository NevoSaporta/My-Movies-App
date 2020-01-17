package com.android.academy.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.R
import kotlinx.android.synthetic.main.activity_bg_services.*

class BGServiceActivity:AppCompatActivity() {
    private var backGroundProgressReceiver:BackGroundProgressReceiver? = null

    companion object{
        const val PROGRESS_UPDATE_ACTION = "PROGRESS_UPDATE_ACTION"
        const val PROGRESS_VALUE_KEY = "PROGRESS_VALUE_KEY"
        const val SERVICE_STATUS ="SERVICE_STATUS"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bg_services)
    }

    override fun onResume() {
        subscribeForProgressUpdates()
        super.onResume()
    }

    override fun onPause() {
        backGroundProgressReceiver?.let {
            unregisterReceiver(backGroundProgressReceiver)
        }
        super.onPause()
    }

    private fun subscribeForProgressUpdates() {
        if(backGroundProgressReceiver==null){
            backGroundProgressReceiver= BackGroundProgressReceiver()
        }
        val progressUpdateActionFilter =IntentFilter(PROGRESS_UPDATE_ACTION)
        registerReceiver(backGroundProgressReceiver,progressUpdateActionFilter)
    }

    inner class BackGroundProgressReceiver: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent) {
            val progress = intent.getIntExtra(PROGRESS_VALUE_KEY,-1)
            progress_percentages.text = progress.toString()
        }
    }
}

