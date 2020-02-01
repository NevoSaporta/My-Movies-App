package com.android.academy.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.R
import com.android.academy.database.AppDatabase
import com.android.academy.model.MovieModel
import com.android.academy.model.MovieModelConverter
import com.android.academy.networking.MoviesResultsBase
import com.android.academy.networking.MoviesService
import com.android.academy.services.BGServiceActivity
import com.android.academy.services.WorkManagerActivity
import com.android.academy.threads.AsyncTaskActivity
import com.android.academy.threads.ThreadsActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
            R.id.work_manager_menu_item ->{
                val intent = Intent(activity,WorkManagerActivity::class.java)
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
        val cachedMovies: List<MovieModel> = AppDatabase.getInstance(activity!!.applicationContext)!!.movieDao()!!.getAll()
        if (cachedMovies.isNotEmpty()){
            movies.addAll(cachedMovies)
            moviesAdapter.setData(movies)
            moviesRcv.adapter?.notifyDataSetChanged()
        }

        val call :Call<MoviesResultsBase> = MoviesService.RestClient.moviesService.loadPopularMovies()
        call.enqueue(object : Callback<MoviesResultsBase> {
            override fun onFailure(call: Call<MoviesResultsBase>, t: Throwable) {
                Log.i(TAG, "not good")
            }

            override fun onResponse(call: Call<MoviesResultsBase>, response: Response<MoviesResultsBase>) {
                if (response.isSuccessful) {
                    response.body()?.let { resultsBase ->
                        AppDatabase.getInstance(activity!!.applicationContext)!!.movieDao()!!.deleteAll()
                        AppDatabase.getInstance(activity!!.applicationContext)!!.movieDao()!!.insertAll(MovieModelConverter.movieConvert(resultsBase.results))
                        movies.addAll( MovieModelConverter.movieConvert(resultsBase.results))
                        moviesAdapter.setData(movies)
                        moviesRcv.adapter?.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    private fun initRecyclerView(){
        context?.let {
            moviesRcv.layoutManager = LinearLayoutManager(it)

            moviesAdapter = MoviesViewAdapter(context = it,movieClickListener = this@MoviesFragment)

            movies.clear()
            loadMovies()

            moviesRcv.adapter =moviesAdapter
        }
    }

    override fun onMovieClicked(movieModel: MovieModel) {
        listener?.onMovieClicked(movieModel)
    }

    override fun onStop() {
        super.onStop()
        AppDatabase.destroyInstance()
    }
}