package com.android.academy.download

class DownloadThread(private val imageUrl: String, private val downloadCallBack: DownloadCallBack):Thread() {

    override fun run() {
        super.run()
    }

    private var progress = 0
    private var lastUpdateTime: Long = 0

    interface DownloadCallBack {
        fun onProgressUpdate(percent: Int)
        fun onDownloadFinished(filePath: String)
        fun onError(error: String)
    }

}