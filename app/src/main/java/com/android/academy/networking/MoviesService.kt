package com.android.academy.networking

import retrofit2.Call
import retrofit2.http.GET

interface MoviesService {

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org"
        const val BASE_API_URL = "$BASE_URL/3/"
        private const val POPULAR = "movie/popular"
        /* api key */
        private const val apiKey = "8ab36487b56b337252b05a364e271dd3"
        private const val keyQuery = "?api_key=$apiKey"
        private const val POPULAR_QUERY_PATH = POPULAR + keyQuery
    }
    @GET(POPULAR_QUERY_PATH)
    abstract fun loadPopularMovies(): Call<Results>

}