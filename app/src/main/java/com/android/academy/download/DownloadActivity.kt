package com.android.academy.download

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.academy.R
import com.android.academy.model.MovieModel

class DownloadActivity : AppCompatActivity() {


    private val isPermissionGranted: Boolean
        get() = ContextCompat.checkSelfPermission(
            this,
            PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(broadcastReceiver, IntentFilter(DownloadService.BROADCAST_ACTION))
    }


    override fun onStop() {
        // unregister local broadcast
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
        super.onStop()
    }


    companion object {
        const val TAG = "DownloadActivity"

        private const val ARG_MOVIE_MODEL = "arguments"
        private const val PERMISSION = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        private const val PERMISSIONS_REQUEST_CODE = 7777
        const val ARG_FILE_PATH: String = "Image-File-Path"

        fun startActivity(context: Context, movieModel: MovieModel) {
            val intent = Intent(context, DownloadActivity::class.java)
            intent.putExtra(ARG_MOVIE_MODEL, movieModel)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download)

        broadcastReceiver = object : BroadcastReceiver() {

            override fun onReceive(context: Context, intent: Intent) {
                val filePath = intent.getStringExtra(ARG_FILE_PATH)
                Log.d(TAG, "DownloadActivity # onReceive, filePath: " + (filePath ?: return))
                if (!TextUtils.isEmpty(filePath)) {
                    showImage(filePath)
                }

            }
            private fun showImage(filePath: String?) {
                val imageView = findViewById<ImageView>(R.id.download_iv_big_image)
                val bitmap = BitmapFactory.decodeFile(filePath)
                imageView.setImageBitmap(bitmap)
            }

        }

        if (isPermissionGranted) {
            startDownloadService()
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
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.download_dialog_title)
        builder.setMessage(R.string.download_dialog_message)
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
        val movieModel = intent.getParcelableExtra<MovieModel>(ARG_MOVIE_MODEL)
        movieModel?.let {
            DownloadService.startService(this,it.backgroundRes)
        }
    }
}
