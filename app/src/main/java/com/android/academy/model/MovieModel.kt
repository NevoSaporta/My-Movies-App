package com.android.academy.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.academy.networking.MoviesService
import com.android.academy.networking.MoviesResults
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class MovieModel (
    @PrimaryKey
    val id :Int ,
    val name :String,
    val imageRes: String,
    val overview: String ="",
    var url: String ="",
    val backgroundRes: String,
    var popularity:Double
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
                "${MoviesService.BACKDROP_BASE_URL}${it.backdrop_path}",
                it.popularity
            )
        }
    }
}