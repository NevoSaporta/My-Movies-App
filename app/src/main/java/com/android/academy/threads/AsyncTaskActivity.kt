package com.android.academy.threads

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.R

class AsyncTaskActivity:AppCompatActivity(), IAsyncTaskEvents ,CounterFragemntHolder{
    private lateinit var counterFragment: CounterFragment
    private lateinit var counterAsyncTask :CounterAsyncTask
    private var taskStarted = false
    private var taskCreated = false
    private  var currentNum :Int = 0
    companion object {
        private const val CURRENT_NUM_KEY ="current_num"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activities_threads)

        counterFragment = CounterFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.activities_threads_frame,counterFragment)
            .commit()

        if (savedInstanceState!=null){
            val startingNum = savedInstanceState.getInt(CURRENT_NUM_KEY)
            if(startingNum!=0){
                val params = mutableListOf<Int>()
                for (num in startingNum downTo  1){
                    params.add(num)
                }
                counterAsyncTask = CounterAsyncTask(this)
                counterAsyncTask.execute(*(params.toTypedArray()))
                taskCreated = true
                taskStarted = true
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (currentNum!=0){
            outState.putInt(CURRENT_NUM_KEY,currentNum)
        }
        cancelTask()
    }

    override fun onPreExecute() {
        if(counterFragment.isVisible){
            counterFragment.setMainTextView("Movie starting in:")
        }
    }

    override fun onPostExecute(result: String?) {
            result?.let {
                counterFragment.setMainTextView(it)
                currentNum=0
                taskStarted =false
                taskCreated =false
            }
    }

    override fun onProgressUpdate(num: Int) {
        counterFragment.setMainTextView(num.toString())
        currentNum =num
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
        cancelTask()
    }

    private fun cancelTask() {
        if (taskCreated) {
            currentNum=0
            counterAsyncTask.cancel(true)
            taskCreated = false
            taskStarted = false
        }
    }
}