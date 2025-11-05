package com.kukareku.readonline.utils

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import com.google.android.material.snackbar.Snackbar
import java.io.File

class DownloadManagerHelper(private val context: Context) {

    fun downloadFile(url: String, title: String, viewForSnackbar: android.view.View, onDone: (Uri?) -> Unit) {
        val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val fileName = title.replace("\\s+".toRegex(), "_") + ".pdf"
        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(title)
            .setDescription("Downloading book…")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        val downloadId = dm.enqueue(request)
        Snackbar.make(viewForSnackbar, "Downloading…", Snackbar.LENGTH_SHORT).show()

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == downloadId) {
                    val query = DownloadManager.Query().setFilterById(id)
                    val cursor = dm.query(query)
                    if (cursor.moveToFirst()) {
                        val uriStr = cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI))
                        onDone(Uri.parse(uriStr))
                    }
                    cursor.close()
                    context.unregisterReceiver(this)
                }
            }
        }
        context.registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }
}
