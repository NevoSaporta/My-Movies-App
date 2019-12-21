package com.android.academy.list

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.academy.R
import com.android.academy.model.MovieModel
import kotlinx.android.synthetic.main.activity_movies.*

class MoviesActivity : AppCompatActivity(),OnMovieClickListener {

    private val movies: MutableList<MovieModel> = mutableListOf()
    private lateinit var moviesAdapter: MoviesViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        loadMovies()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        movies_rv_list.layoutManager = LinearLayoutManager(this@MoviesActivity)
        // Create Movies Adapter
        moviesAdapter = MoviesViewAdapter(
            context = this@MoviesActivity,
            movieClickListener = this@MoviesActivity
        )

        // Attach Adapter to RecyclerView
        movies_rv_list.adapter = moviesAdapter

        // Populate Adapter with data
        moviesAdapter.setData(movies)

    }

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

    override fun onMovieClicked(movieModel: MovieModel) {
        Toast.makeText(this, movieModel.name, Toast.LENGTH_SHORT).show()
    }

}