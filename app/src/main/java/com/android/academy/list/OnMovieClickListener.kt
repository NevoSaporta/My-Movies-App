package com.android.academy.list

import com.android.academy.model.MovieModel

interface OnMovieClickListener {
    fun onMovieClicked(movieModel: MovieModel)
}