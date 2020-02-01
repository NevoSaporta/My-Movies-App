package com.android.academy.model

import com.android.academy.networking.MoviesResults
import com.android.academy.networking.MoviesService
import com.android.academy.networking.TrailerResults
import com.android.academy.networking.TrailerResultsBase

object MovieModelConverter{
    fun movieConvert(result:List<MoviesResults>):List<MovieModel>{
        return result.map {
            MovieModel(
                it.id,
                it.title,
                "${MoviesService.POSTER_BASE_URL}${it.poster_path}",
                it.overview,
                "",
                "${MoviesService.BACKDROP_BASE_URL}${it.backdrop_path}",
                it.popularity
            )
        }
    }
    fun videoConvert(resultsBase: TrailerResultsBase):TrailerModel?{
        val results: List<TrailerResults> = resultsBase.results
        if (results.isNotEmpty()) {
            val videoResult: TrailerResults = results[0]
            return TrailerModel(resultsBase.id, videoResult.id, videoResult.key)
        }
        return null
    }
}