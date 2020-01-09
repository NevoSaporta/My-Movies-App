package com.android.academy.threads

import android.os.AsyncTask

class CounterAsyncTask(private val iAsyncTaskEvents: IAsyncTaskEvents):AsyncTask<Int,Int,String>(){
    override fun doInBackground(vararg numbers: Int?): String {
        for(number in numbers){
            if(isCancelled){
                break
            }
            publishProgress(number)
            Thread.sleep(5000)
        }
        return "Done!"
    }

    override fun onProgressUpdate(vararg values: Int?) {
        iAsyncTaskEvents.onProgressUpdate(values[0]!!)
    }

    override fun onPostExecute(result: String?) {
        iAsyncTaskEvents.onPostExecute(result)
    }

    override fun onCancelled() {
        iAsyncTaskEvents.onCancel()
    }

}