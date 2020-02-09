package com.android.academy.download

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.academy.R
import com.android.academy.model.MovieModel

class DownloadActivity : AppCompatActivity() {

    private val isPermissionGranted: Boolean
        get() = ContextCompat.checkSelfPermission(
            this,
            PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    companion object {

        private const val ARG_MOVIE_MODEL = "arguments"
        private const val PERMISSION = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        private const val PERMISSIONS_REQUEST_CODE = 7777
        lateinit var builder :AlertDialog.Builder

        fun startActivity(context: Context, movieModel: MovieModel) {
            val intent = Intent(context, DownloadActivity::class.java)
            intent.putExtra(ARG_MOVIE_MODEL, movieModel)
            builder = AlertDialog.Builder(context)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)

        if (isPermissionGranted) {
        } else {
            requestPermission()
        }
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, PERMISSION)) {
            showExplainingRationaleDialog()
        } else {
            requestWritePermission()
        }
    }

    private fun showExplainingRationaleDialog() {
        builder.setPositiveButton("ok") { dialogInterface, i -> requestWritePermission() }
        builder.setNegativeButton("cancel") { dialogInterface, i ->
            this.finish()
        }
        builder.create().show()
    }

    private fun requestWritePermission() {
        ActivityCompat.requestPermissions(this, arrayOf(PERMISSION), PERMISSIONS_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, yay! Do the contacts-related task you need to do.
                Log.d("TAG", "DownloadActivity # onRequestPermissionsResult, Permission granted")
                startDownloadService()
            } else {
                // permission denied, boo! Disable the functionality that depends on this permission.
                Log.d("TAG", "DownloadActivity # onRequestPermissionsResult, Permission denied")
                // no Permission - finish activity
                this.finish()
            }
        }
    }

    private fun startDownloadService() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
