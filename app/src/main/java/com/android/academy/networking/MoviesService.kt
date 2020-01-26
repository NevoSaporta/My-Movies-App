package com.android.academy.networking

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface MoviesService {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org"
        const val BASE_API_URL = "$BASE_URL/3/"
        private const val POPULAR = "movie/popular"
        /* api key */
        private const val apiKey = "8ab36487b56b337252b05a364e271dd3"
        private const val keyQuery = "?api_key=$apiKey"
        const val POPULAR_QUERY_PATH =POPULAR + keyQuery
    }

    @Headers("content-type:application/json;charset=utf-8")
    @GET(POPULAR_QUERY_PATH)
    fun loadPopularMovies(): Call<ResultsBase>

}