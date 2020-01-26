package com.android.academy.details

import android.content.Intent
import android.net.Uri
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
    private lateinit var backImage :ImageView
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
        posterImage = view.findViewById(R.id.fd_main_img)
        titleText = view.findViewById(R.id.fd_movie_name_txt)
        releaseDateText = view.findViewById(R.id.fd_released_date)
        trailerButton = view.findViewById(R.id.fd_movie_trailer_btn)
        overviewText = view.findViewById(R.id.fd_overview_text)
        backImage =view.findViewById(R.id.fd_background_img)
    }
    fun loadMovie(movie: MovieModel){
        titleText.text = movie.name
        overviewText.text = movie.overview
        //posterImage.setImageResource(movie.imageRes)
        //backImage.setImageResource(movie.backgroundRes)
        trailerButton.setOnClickListener {
            val webpage:Uri = Uri.parse(movie.url)
            val intent = Intent(Intent.ACTION_VIEW,webpage)
            if(intent.resolveActivity(activity!!.packageManager)!=null){
                startActivity(intent)
            }
        }
    }

}