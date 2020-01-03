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
    private  var index: Int =0
    companion object{
        private const val MOVIES_BUNDLE_KEY ="unique_movies_holder_key"
        private const val INDEX_BUNDLE_KEY ="unique_index_holder_key"
        fun newInstance(movies:List<MovieModel>,index:Int):DetailsFragmentHolder{
            val fragment =DetailsFragmentHolder()
            val args = Bundle()
            args.putParcelableArrayList(MOVIES_BUNDLE_KEY,ArrayList(movies))
            args.putInt(INDEX_BUNDLE_KEY,index)
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
        detailsPager = view.findViewById(R.id.fragment_details_holder_pager)
        movies = arguments?.getParcelableArrayList(MOVIES_BUNDLE_KEY)
        index =arguments!!.getInt(INDEX_BUNDLE_KEY)
        val fragments =movies!!.map {
            DetailsFragment.newInstance(it)
        }
        activity?.let {
            pagerAdapter = DetailsFragmentPagerAdapter(it.supportFragmentManager,fragments)

            detailsPager.adapter =pagerAdapter
            detailsPager.currentItem=index
        }
        return view
    }

}