package com.android.academy.networking

import TrailerResultsBase
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesService {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org"
        const val BASE_API_URL = "$BASE_URL/3/"

        private const val POPULAR = "movie/popular"
        private const val TRAILER ="movie/{movie_id}/videos"

        const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
        const val BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w780"

        /* api key */
        private const val apiKey = "8ab36487b56b337252b05a364e271dd3"
        private const val keyQuery = "?api_key=$apiKey"
        const val POPULAR_QUERY_PATH =POPULAR + keyQuery
        const val TRAILER_QUERY_PATH =TRAILER + keyQuery
    }

    object RestClient {
        private val retrofit : Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_API_URL)
            .build()


        val moviesService: MoviesService = retrofit.create(MoviesService::class.java)

    }
    @GET(POPULAR_QUERY_PATH)
    fun loadPopularMovies(): Call<MoviesResultsBase>

    @GET(TRAILER_QUERY_PATH)
    fun loadTrailer(@Path("movie_id") movieId: String):Call<TrailerResultsBase>
}