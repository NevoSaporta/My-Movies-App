package com.android.academy.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.academy.R

class MockFragment:Fragment(){
    private lateinit var mockTextView :TextView
    lateinit var mockText :String
    companion object{
        fun newInstance(mockText:String):MockFragment{
            val fragment =MockFragment()
            fragment.mockText = mockText
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =inflater.inflate(R.layout.mock_fragment,container,false)
        mockTextView = view.findViewById(R.id.mock_text)
        mockTextView.text = mockText
        return view
    }

}