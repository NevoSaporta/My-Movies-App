package com.android.academy.threads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.academy.R

class CounterFragment:Fragment() {

    private lateinit var mainText :TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_counter,container,false)
        mainText = view.findViewById(R.id.cf_main_text_view)
        return view
    }
    fun setMainTextView(newText:String){
        mainText.text = newText
    }
}