package com.android.academy.model

import android.os.Parcelable
import com.android.academy.networking.MoviesService
import com.android.academy.networking.MoviesResults
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel (
    val id :Int ,
    val name :String,
    val imageRes: String,
    val overview: String ="",
    var url: String ="",
    val backgroundRes: String
):Parcelable

object MovieModelConverter{
    fun movieConvert(result:List<MoviesResults>):List<MovieModel>{
        return result.map {
            MovieModel(
                it.id,
                it.title,
                "${MoviesService.POSTER_BASE_URL}${it.poster_path}",
                it.overview,
                "",
                "${MoviesService.BACKDROP_BASE_URL}${it.backdrop_path}"
            )
        }
    }
}