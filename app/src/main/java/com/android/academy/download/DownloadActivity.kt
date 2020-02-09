package com.android.academy.download

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.academy.R
import com.android.academy.model.MovieModel

class DownloadActivity : AppCompatActivity(){
    companion object {

        private const val ARG_MOVIE_MODEL ="arguments"

        fun startActivity(context: Context, movieModel: MovieModel) {
            val intent = Intent(context, DownloadActivity::class.java)
            intent.putExtra(ARG_MOVIE_MODEL, movieModel)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)
    }
}