package com.android.academy.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.academy.R
import com.android.academy.database.AppDatabase
import com.android.academy.model.MovieModel
import com.android.academy.model.TrailerModel
import com.android.academy.networking.MoviesService
import com.android.academy.networking.TrailerResultsBase
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsFragment:Fragment(){

    private lateinit var posterImage :ImageView
    private lateinit var backImage :ImageView
    private lateinit var titleText :TextView
    private lateinit var releaseDateText :TextView
    private lateinit var trailerButton :Button
    private lateinit var overviewText :TextView

    companion object{
        private const val MOVIE_BUNDLE_KEY ="unique_movie_key"
        private const val YOUTUBE_URL ="https://www.youtube.com/watch?v="
         val TAG =DetailsFragment::class.simpleName

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
        Log.i(TAG,movie!!.url)
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
        if (movie.imageRes.isNotEmpty()) {
            Picasso.get()
                .load(movie.imageRes)
                .into(posterImage)
        }
        if (movie.backgroundRes.isNotEmpty()) {
            Picasso.get()
                .load(movie.backgroundRes)
                .into(backImage)
        }
        trailerButton.setOnClickListener {
            val trailerModel = AppDatabase.getInstance(activity!!.applicationContext)!!.trailerDao()!!.getVideo(movie.id)
            if(trailerModel !=null){
                movie.url = "${YOUTUBE_URL}${trailerModel.key}"
                setIntent(movie)
            }
            else{
                val call :Call<TrailerResultsBase> = MoviesService.RestClient.moviesService.loadTrailer(movie.id.toString())
                call.enqueue(object : Callback<TrailerResultsBase> {
                    override fun onFailure(call: Call<TrailerResultsBase>, t: Throwable) {
                        Log.i(TAG,"not good")
                    }
                    override fun onResponse(call: Call<TrailerResultsBase>, response: Response<TrailerResultsBase>) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                val result = it.results[0]
                                AppDatabase.getInstance(activity!!.applicationContext)!!.trailerDao()!!.insert(
                                    TrailerModel(movie.id,result.id,result.key)
                                )
                                movie.url = "${YOUTUBE_URL}${result.key}"
                                setIntent(movie)
                            }
                        }
                    }
                })
            }
        }
    }

    private fun setIntent(movie: MovieModel) {
        val webpage: Uri = Uri.parse(movie.url)
        val intent = Intent(Intent.ACTION_VIEW, webpage)
        if (intent.resolveActivity(activity!!.packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun onStop() {
        super.onStop()
        AppDatabase.destroyInstance()
    }
}