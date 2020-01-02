package com.android.academy.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.academy.R
import com.android.academy.model.MovieModel

class DetailsFragment:Fragment(){

    private lateinit var posterImage :ImageView
    private lateinit var titleText :TextView
    private lateinit var releaseDateText :TextView
    private lateinit var trailerButton :Button
    private lateinit var overviewText :TextView

    companion object{
        private const val MOVIE_BUNDLE_KEY ="unique_movie_key"

        fun newInstance(movie :MovieModel):DetailsFragment{
            val fragment =DetailsFragment()
            val args = Bundle()
            args.putParcelable(MOVIE_BUNDLE_KEY,movie)
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =inflater.inflate(R.layout.fragment_details,container,false)
        val movie:MovieModel? = arguments?.getParcelable(MOVIE_BUNDLE_KEY)
        initViews(view)
        movie?.let (::loadMovie)
        return view
    }

    private fun initViews(view: View) {
        posterImage = view.findViewById(R.id.main_img)
        titleText = view.findViewById(R.id.movie_name_txt)
        releaseDateText = view.findViewById(R.id.released_date)
        trailerButton = view.findViewById(R.id.movie_trailer_btn)
        overviewText = view.findViewById(R.id.overview_text)

    }
    fun loadMovie(movie: MovieModel){
        titleText.text = movie.name
        overviewText.text = movie.overview
        posterImage.setImageResource(movie.imageRes)
    }

}