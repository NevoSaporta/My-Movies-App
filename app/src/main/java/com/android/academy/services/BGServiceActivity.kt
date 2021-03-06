package com.android.academy.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.R
import kotlinx.android.synthetic.main.activity_bg_services.*
import java.util.*

class BGServiceActivity:AppCompatActivity() {

    private var backGroundProgressReceiver:BackGroundProgressReceiver? = null
    private var isIntentServiceStarted  =false
    private var isServiceStarted  =false

    companion object{
        const val PROGRESS_UPDATE_ACTION = "PROGRESS_UPDATE_ACTION"
        const val PROGRESS_VALUE_KEY = "PROGRESS_VALUE_KEY"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bg_services)
        bga_start_intent_service_button.setOnClickListener {
            if(isServiceStarted){
                stopService(Intent(this, HardJobService::class.java))
                isServiceStarted=false
            }
            if(!isIntentServiceStarted){
                isIntentServiceStarted = true
                startService(Intent(this,HardJobIntentService::class.java))
            }
        }
        bga_start_service_button.setOnClickListener {
            if(isIntentServiceStarted){
                stopService(Intent(this, HardJobIntentService::class.java))
                isIntentServiceStarted=false
            }
            if(!isServiceStarted){
                isServiceStarted = true
                startService(Intent(this,HardJobService::class.java))
            }
        }
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
            if(progress>=0){
                val text:String
                if(progress==100){
                    text="Done!"
                    isServiceStarted =false
                    isIntentServiceStarted =false
                }else{
                    text = String.format(Locale.getDefault(),"%d%%",progress)
                }
                bga_progress_percentages.text = text
            }
        }
    }

}

