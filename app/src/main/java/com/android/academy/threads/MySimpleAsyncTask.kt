package com.android.academy.threads

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import java.lang.Exception

class MySimpleAsyncTask(private val iAsyncTaskEvents: IAsyncTaskEvents) {
    private val mainLooper = Looper.getMainLooper()

    private val mainHandler = Handler(mainLooper)

    private lateinit var handlerThread :HandlerThread
    private lateinit var looper :Looper
    private lateinit var handler :Handler

    private var isCancelled =false

    fun execute(){
        handlerThread = HandlerThread("worker_tag")
        handlerThread.start()
        looper =handlerThread.looper
        handler = Handler(looper)
        mainHandler.post{
            onPreExecute()
        }
    }

    private fun onPreExecute(){
            iAsyncTaskEvents.onPreExecute()
            isCancelled =false
    }
    fun doInBackground(vararg numbers: Int?){
        handler.post {
            for(number in numbers){
                if(isCancelled){
                    return@post
                }
                publishProgress(number)
                Thread.sleep(500)
            }
            onPostExecute("Done!")
        }
    }

    private fun publishProgress(vararg values: Int?){
        mainHandler.post {
            iAsyncTaskEvents.onProgressUpdate(values[0]!!)
        }
    }

    private fun onPostExecute(result: String?){
        mainHandler.post {
            iAsyncTaskEvents.onPostExecute(result)
        }
    }

    fun cancel(){
        isCancelled =true
        handlerThread.quit()
        mainHandler.post {
            iAsyncTaskEvents.onCancel()
        }
    }
}