package com.android.academy.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.R
import com.android.academy.model.MovieModel

class MoviesFragment :Fragment(), OnMovieClickListener {

    private lateinit var moviesRcv : RecyclerView
    private val movies :MutableList<MovieModel> = ArrayList()
    private lateinit var moviesAdapter : MoviesViewAdapter
    private var listener :OnMovieClickListener? =null

    companion object{
        val TAG =MoviesFragment::class.simpleName
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnMovieClickListener){
            listener =context
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies,container,false)
        moviesRcv =view.findViewById(R.id.fragment_movies_rcv)
        movies.clear()
        loadMovies()
        initRecyclerView()
        return view
    }
    fun getMovies():List<MovieModel> =movies
     private fun loadMovies() {
        movies.add(
            MovieModel(
                getString(R.string.black_panther_name),
                R.drawable.black_panther,
                getString(R.string.black_panther_overview)
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.deadpool2_name),
                R.drawable.deadpool2,
                getString(R.string.deadpool2_overview)
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.guardians_of_the_galaxy_name),
                R.drawable.guardians_of_the_galaxy,
                getString(R.string.guardians_of_the_galaxy_overview)
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.infinity_war_name),
                R.drawable.infinity_war,
                getString(R.string.infinity_war_overview)
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.interstellar_name),
                R.drawable.interstellar,
                getString(R.string.interstellar_overview)
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.jurrasic_world_name),
                R.drawable.jurassic_world,
                getString(R.string.jurassic_world_overview)
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.oceans8_name),
                R.drawable.oceans8,
                getString(R.string.oceans_8_overview)
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.the_first_purge_name),
                R.drawable.the_first_purge,
                getString(R.string.the_firs_purge_overview)
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.the_meg_name),
                R.drawable.the_meg,
                getString(R.string.the_meg_overview)
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.thor_ragnarok_name),
                R.drawable.thor_ragnarok,
                getString(R.string.thor_ragnarok_overview)
            )
        )
    }

    private fun initRecyclerView(){
        context?.let {
            moviesRcv.layoutManager = LinearLayoutManager(it)

            moviesAdapter = MoviesViewAdapter(context = it,movieClickListener = this@MoviesFragment)

            moviesRcv.adapter =moviesAdapter

            moviesAdapter.setData(movies)
        }
    }

    override fun onMovieClicked(movieModel: MovieModel) {
        listener?.onMovieClicked(movieModel)
    }
}