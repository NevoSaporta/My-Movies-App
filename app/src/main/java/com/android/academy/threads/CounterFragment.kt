package com.android.academy.threads

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.academy.R

class CounterFragment:Fragment() {

    private lateinit var mainText :TextView
    private lateinit var createButton :Button
    private lateinit var startButton :Button
    private lateinit var cancelButton :Button

    private var listener :CounterFragemntHolder? =null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is CounterFragemntHolder){
            listener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener =null

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_counter,container,false)
        initViews(view)
        return view
    }

    private fun initViews(view: View){
        mainText = view.findViewById(R.id.cf_main_text_view)
        setMainTextView("This is ${activity!!.localClassName.removePrefix(activity!!.packageName)}")
        createButton = view.findViewById(R.id.cf_create_button)
        createButton.setOnClickListener {
            listener?.onCreatePressed()
        }
        startButton = view.findViewById(R.id.cf_start_button)
        startButton.setOnClickListener {
            listener?.onStartPressed()
        }
        cancelButton = view.findViewById(R.id.cf_cancel_button)
        cancelButton.setOnClickListener {
            listener?.onCancelPressed()
        }
    }
    fun setMainTextView(newText:String){
        mainText.text = newText
    }
}