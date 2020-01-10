package com.android.academy.threads

import android.os.Handler
import android.os.Looper

class MySimpleAsyncTask {
    private val mainLooper = Looper.getMainLooper()

    val mainHandler = Handler(mainLooper)

}