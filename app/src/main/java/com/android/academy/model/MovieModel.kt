package com.android.academy.model

import android.os.Parcelable
import com.android.academy.networking.Results
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieModel (
    val name :String,
    val imageRes: String,
    val overview: String ="",
    val url: String ="",
    val backgroundRes: String
):Parcelable

object MovieModelConverter{
    fun movieConvert(result:List<Results>):List<MovieModel>{
        return result.map {
            MovieModel(
                it.title,
                /*it.poster_path,*/"",
                it.overview,
                it.video.toString(),
                ""/*it.backdrop_path*/
            )
        }
    }
}