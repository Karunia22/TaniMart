package com.example.tanimart.data.seller

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

object ReportDownloader {
    fun downloadPdf(
        context: Context,
        url: String,
        token: String,
        fileName: String
    ) {
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle("Laporan Penjualan")
            .setDescription(fileName)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            .addRequestHeader("Authorization", "Bearer $token")
            .addRequestHeader("Accept", "application/pdf")

        val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        dm.enqueue(request)
    }
}
