package com.android.academy.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.academy.R

class DetailsFragment:Fragment(){
    private lateinit var nameTextView :TextView
    lateinit var nameText :String
    companion object{
        fun newInstance(name:String):DetailsFragment{
            val fragment =DetailsFragment()
            fragment.nameText = name
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =inflater.inflate(R.layout.fragment_details,container,false)
        nameTextView = view.findViewById(R.id.movie_name_txt)
        nameTextView.text = nameText
        return view
    }

}