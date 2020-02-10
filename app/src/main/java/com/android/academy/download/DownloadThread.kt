package com.android.academy.download

import android.os.Environment
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class DownloadThread(private val imageUrl: String, private val downloadCallBack: DownloadCallBack):Thread() {

    override fun run() {
        val file = createFile()
        if (file == null) {
            downloadCallBack.onError("Can't create file")
            return
        }
        var connection: HttpURLConnection? = null
        var inputStream: InputStream? = null
        var fos: FileOutputStream? = null


        val url = URL(imageUrl)
        connection = url.openConnection() as HttpURLConnection
        connection.connect()

        if (connection.responseCode != HttpURLConnection.HTTP_OK) {
            downloadCallBack.onError(
                "Server returned HTTP response code: "
                        + connection.responseCode + " - " + connection.responseMessage
            )
        }
        val fileLength = connection.contentLength

        // Input stream (Downloading file)
        inputStream = BufferedInputStream(url.openStream(), 8192)

        // Output stream (Saving file)
        fos = FileOutputStream(file.path)

        var next: Int
        val data = ByteArray(1024)
        while (inputStream.read(data).let {next = it; it != -1 }){   //JAVA = while ((next = inputStream.read(data)) != -1)

            fos.write(data, 0, next)

            updateProgress(fos, fileLength)
        }

        downloadCallBack.onDownloadFinished(file.path)
    }

    @Throws(IOException::class)
    private fun updateProgress(fos: FileOutputStream, fileLength: Int) {
        if (lastUpdateTime == 0L || System.currentTimeMillis() > lastUpdateTime + 500) {
            val count = fos.channel.size().toInt() * 100 / fileLength
            if (count > progress) {
                progress = count
                lastUpdateTime = System.currentTimeMillis()
                downloadCallBack.onProgressUpdate(progress)
            }
        }
    }


    private fun createFile(): File? {
        val mediaStorageDirectory = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + File.separator
        )
        // Create the storage directory if it does not exist
        if (!mediaStorageDirectory.exists()) {
            if (!mediaStorageDirectory.mkdirs()) {
                return null
            }
        }
        // Create a media file name
        val imageName = createImageFileName() + ".jpg"
        return File(mediaStorageDirectory.path + File.separator + imageName)
    }

    private fun createImageFileName(): String {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        return "FILE_$timeStamp"
    }


    private var progress = 0
    private var lastUpdateTime: Long = 0

    interface DownloadCallBack {
        fun onProgressUpdate(percent: Int)
        fun onDownloadFinished(filePath: String)
        fun onError(error: String)
    }

}