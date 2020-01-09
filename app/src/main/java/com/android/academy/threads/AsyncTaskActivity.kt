package com.android.academy.threads

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.R

class AsyncTaskActivity:AppCompatActivity(), IAsyncTaskEvents {
    lateinit var counterFragment: CounterFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activities_threads)

        counterFragment = CounterFragment()
        counterFragment.setMainTextView("This is AsyncTask Activity"
        )
        supportFragmentManager
            .beginTransaction()
            .add(R.id.activities_threads_frame,counterFragment)
            .commit()
    }

    override fun onPreExecute() {
        counterFragment.setMainTextView("This is AsyncTask Activity")
    }

    override fun onPostExecute(result: String?) {
        counterFragment.setMainTextView(result!!)
    }

    override fun onProgressUpdate(num: Int) {
        counterFragment.setMainTextView(num.toString())
    }

    override fun onCancel() {
        counterFragment.setMainTextView("This is AsyncTask Activity")
    }
}