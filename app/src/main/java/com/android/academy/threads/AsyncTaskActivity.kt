package com.android.academy.threads

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.R

class AsyncTaskActivity:AppCompatActivity(), IAsyncTaskEvents ,CounterFragemntHolder{
    private lateinit var counterFragment: CounterFragment
    private lateinit var counterAsyncTask :CounterAsyncTask
    private var taskStarted = false
    private var taskCreated = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activities_threads)

        counterFragment = CounterFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.activities_threads_frame,counterFragment)
            .commit()
    }

    override fun onPreExecute() {
        counterFragment.setMainTextView("EventTask Created!")
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

    override fun onCreatePressed() {
        if(!taskCreated){
            if(!taskStarted){
                counterAsyncTask = CounterAsyncTask(this )
                taskCreated = true
            }
            else{
                Toast.makeText(this,"task has already started",Toast.LENGTH_LONG).show()
            }
        }
        else{
            Toast.makeText(this,"task has already created",Toast.LENGTH_LONG).show()
        }
    }

    override fun onStartPressed() {
        if(taskCreated){
            if(!taskStarted){
                counterAsyncTask.execute(10,9,8,7,6,5,4,3,2,1)
                taskStarted =true
            }
            else{
                Toast.makeText(this,"task has already started",Toast.LENGTH_LONG).show()
            }
        }
        else{
            Toast.makeText(this,"no task has created",Toast.LENGTH_LONG).show()
        }
    }

    override fun onCancelPressed() {
        if(taskCreated){
            counterAsyncTask.cancel(true)
            taskCreated= false
            taskStarted =false
        }
    }
}