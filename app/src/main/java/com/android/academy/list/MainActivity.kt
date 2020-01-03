package com.android.academy.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.R
import com.android.academy.details.DetailsFragmentHolder
import com.android.academy.model.MovieModel

class MainActivity : AppCompatActivity(),OnMovieClickListener{

    private lateinit var moviesFragment :MoviesFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        moviesFragment=
        if(savedInstanceState==null) {
            MoviesFragment().also{
                supportFragmentManager
                    .beginTransaction()
                    .add(R.id.activity_movies_frame,it,MoviesFragment.TAG)
                    .commit()
            }
        }else{
            supportFragmentManager.findFragmentByTag(MoviesFragment.TAG) as MoviesFragment
        }
    }

    override fun onMovieClicked(movieModel: MovieModel) {
        val movies =moviesFragment.getMovies()
        val detailsFragmentHolder = DetailsFragmentHolder.newInstance(
            movies,movies.indexOf(movieModel)
        )
        supportFragmentManager
            .beginTransaction()
            .addToBackStack( null)
            .replace(R.id.activity_movies_frame,detailsFragmentHolder)
            .commit()
    }

}