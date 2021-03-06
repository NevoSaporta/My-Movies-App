package com.android.academy.threads

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.R

class ThreadsActivity: AppCompatActivity() ,IAsyncTaskEvents , CounterFragemntHolder {

    private lateinit var counterFragment: CounterFragment
    private val  mySimpleAsyncTask = MySimpleAsyncTask(this)
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
                mySimpleAsyncTask.execute()
                mySimpleAsyncTask.doInBackground(*(params.toTypedArray()))
                taskCreated = true
                taskStarted = true
            }
        }
    }

    override fun onPreExecute() {
        if(counterFragment.isVisible) {
            counterFragment.setMainTextView("Task Created")
        }
    }

    override fun onSaveInstanceState(outState: Bundle){
        super.onSaveInstanceState(outState)
        if (currentNum!=0){
            outState.putInt(CURRENT_NUM_KEY,currentNum)
        }
        cancelTask()
    }

    private fun cancelTask() {
        if (taskCreated) {
            currentNum=0
            mySimpleAsyncTask.cancel()
            taskCreated = false
            taskStarted = false
        }
    }

    override fun onPostExecute(result: String?) {
        result?.let {
            counterFragment.setMainTextView(result)
        }
        taskCreated = false
        taskStarted = false
    }

    override fun onProgressUpdate(num: Int) {
        counterFragment.setMainTextView(num.toString())
        currentNum =num
    }

    override fun onCancel() {
        counterFragment.setMainTextView("This is Threads Activity")
    }

    override fun onCreatePressed() {
        if(!taskCreated&&!taskStarted){
            if(!taskStarted){
                mySimpleAsyncTask.execute()
                taskCreated = true
            }
            else{
                Toast.makeText(this,"task has already started", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this,"task has already created", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onStartPressed() {

        if(taskCreated){
            if(!taskStarted){
                mySimpleAsyncTask.doInBackground(10,9,8,7,6,5,4,3,2,1)
                taskStarted =true
            }
            else{
                Toast.makeText(this,"task has already started",Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(this,"no task has created",Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCancelPressed() {
        cancelTask()
    }
}