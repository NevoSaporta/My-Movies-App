package com.android.academy.threads

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.R

class AsyncTaskActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activities_threads)

        val counterFragment = CounterFragment()

        supportFragmentManager
            .beginTransaction()
            .add(R.id.activities_threads_frame,counterFragment)
            .commit()
    }
}