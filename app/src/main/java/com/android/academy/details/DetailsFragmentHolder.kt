package com.android.academy.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.android.academy.R
import com.android.academy.model.MovieModel

class DetailsFragmentHolder:Fragment() {
    private lateinit var detailsPager: ViewPager
    private lateinit var pagerAdapter : DetailsFragmentPagerAdapter
    private  var movies:ArrayList<MovieModel>? =ArrayList()

    companion object{
        private const val MOVIES_BUNDLE_KEY ="unique_movies_holder_key"

        fun newInstance(movies:List<MovieModel>):DetailsFragmentHolder{
            val fragment =DetailsFragmentHolder()
            val args = Bundle()
            args.putParcelableArrayList(MOVIES_BUNDLE_KEY,ArrayList(movies))
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details_holder,container,false)
        detailsPager = view.findViewById(R.id.activity_details_pager)
        movies = arguments?.getParcelableArrayList(MOVIES_BUNDLE_KEY)
        val fragments =movies!!.map {
            DetailsFragment.newInstance(it)
        }
        activity?.let {
            pagerAdapter = DetailsFragmentPagerAdapter(it.supportFragmentManager,fragments)

            detailsPager.adapter =pagerAdapter
        }
        return view
    }

}