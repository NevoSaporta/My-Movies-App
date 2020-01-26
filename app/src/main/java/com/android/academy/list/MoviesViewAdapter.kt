package com.android.academy.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.academy.R
import com.android.academy.model.MovieModel
import kotlinx.android.synthetic.main.item_movie.view.*

class MoviesViewAdapter (private val movieClickListener: OnMovieClickListener ,context:Context) : RecyclerView.Adapter<MoviesViewAdapter.ViewHolder>() {

    private val layoutInflater: LayoutInflater = context
        .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val movies = mutableListOf<MovieModel>()

    fun setData(newItems: List<MovieModel>){
        movies.clear()
        movies.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = layoutInflater
            .inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view,movieClickListener)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    inner class ViewHolder(view: View, movieClickListener: OnMovieClickListener) : RecyclerView.ViewHolder(view) {

        private val ivImage: ImageView = view.im_main_img
        private val tvTitle: TextView = view.im_title_txt
        private val tvOverview: TextView = view.im_overview_text

        private lateinit var movieModel: MovieModel

        init {
            view.setOnClickListener {
                movieClickListener.onMovieClicked(movieModel)
            }
        }

        fun bind(movieModel: MovieModel) {
            //ivImage.setImageResource(movieModel.imageRes)
            tvTitle.text = movieModel.name
            tvOverview.text = movieModel.overview
            this.movieModel = movieModel
        }

    }
}