package com.android.academy.threads

interface IAsyncTaskEvents {
    fun onPreExecute()
    fun onPostExecute(result: String?)
    fun onProgressUpdate(num:Int)
    fun onCancel()
}