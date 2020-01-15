package com.android.academy.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.R
import com.android.academy.model.MovieModel
import com.android.academy.services.BGServiceActivity
import com.android.academy.threads.AsyncTaskActivity
import com.android.academy.threads.ThreadsActivity

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
        movies.clear()
        loadMovies()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies,container,false)
        moviesRcv =view.findViewById(R.id.fragment_movies_rcv)
        initRecyclerView()
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.threads_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.task_menu_item ->{
                val intent = Intent(activity,AsyncTaskActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.handler_menu_item ->{
                val intent = Intent(activity,ThreadsActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.service_menu_item ->{
                val intent = Intent(activity,BGServiceActivity::class.java)
                startActivity(intent)
                true
            }
            else -> {
                 super.onOptionsItemSelected(item)
            }
        }
    }

    fun getMovies():List<MovieModel> =movies

    private fun loadMovies() {
        movies.add(
            MovieModel(
                getString(R.string.black_panther_name),
                R.drawable.black_panther,
                getString(R.string.black_panther_overview),
                getString(R.string.black_panther_url),
                R.drawable.black_panther_back
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.deadpool2_name),
                R.drawable.deadpool2,
                getString(R.string.deadpool2_overview),
                getString(R.string.deadpool2_url),
                R.drawable.deadpool_2_back
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.guardians_of_the_galaxy_name),
                R.drawable.guardians_of_the_galaxy,
                getString(R.string.guardians_of_the_galaxy_overview),
                getString(R.string.guardians_of_the_galaxy_url),
                R.drawable.guardians_of_the_galaxy_back
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.infinity_war_name),
                R.drawable.infinity_war,
                getString(R.string.infinity_war_overview),
                getString(R.string.infinity_war_url),
                R.drawable.infinity_war_background
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.interstellar_name),
                R.drawable.interstellar,
                getString(R.string.interstellar_overview),
                getString(R.string.interstellar_url),
                R.drawable.interstellar_back
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.jurrasic_world_name),
                R.drawable.jurassic_world,
                getString(R.string.jurassic_world_overview),
                getString(R.string.jurassic_world_url),
                R.drawable.jurassic_world_back
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.oceans8_name),
                R.drawable.oceans8,
                getString(R.string.oceans_8_overview),
                getString(R.string.ocenas8_url),
                R.drawable.ocean_eight_back
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.the_first_purge_name),
                R.drawable.the_first_purge,
                getString(R.string.the_firs_purge_overview),
                getString(R.string.the_first_purge_url),
                R.drawable.the_first_purge_back
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.the_meg_name),
                R.drawable.the_meg,
                getString(R.string.the_meg_overview),
                getString(R.string.the_meg_url),
                R.drawable.the_meg_back
            )
        )
        movies.add(
            MovieModel(
                getString(R.string.thor_ragnarok_name),
                R.drawable.thor_ragnarok,
                getString(R.string.thor_ragnarok_overview),
                getString(R.string.thor_ragnarok_url),
                R.drawable.thor_ragnarok_back
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