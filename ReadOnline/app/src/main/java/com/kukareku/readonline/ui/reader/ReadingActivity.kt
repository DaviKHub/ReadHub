package com.kukareku.readonline.ui.reader

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kukareku.readonline.R
import kotlinx.android.synthetic.main.activity_reading.*

class ReadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reading)

        val bookUri = intent.getStringExtra("book_uri")
        val title = intent.getStringExtra("book_title")
        tvTitle.text = title

        btnOpenExternal.setOnClickListener {
            if (bookUri == null) return@setOnClickListener
            val uri = Uri.parse(bookUri)
            val mimetype = when {
                bookUri.endsWith(".pdf", ignoreCase = true) -> "application/pdf"
                bookUri.endsWith(".epub", ignoreCase = true) -> "application/epub+zip"
                else -> "*/*"
            }
            val viewIntent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, mimetype)
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            startActivity(Intent.createChooser(viewIntent, getString(R.string.open_with)))
        }

        // simple progress demo
        btnFakeProgress.setOnClickListener {
            progressBar.isIndeterminate = false
            progressBar.max = 100
            progressBar.progress = 0
            // fake progress
            Thread {
                for (i in 1..100) {
                    Thread.sleep(20)
                    runOnUiThread { progressBar.progress = i }
                }
            }.start()
        }
    }

    private fun getMimeForUri(uri: Uri): String {
        return when {
            uri.toString().endsWith(".pdf", true) -> "application/pdf"
            uri.toString().endsWith(".epub", true) -> "application/epub+zip"
            else -> "*/*"
        }
    }


}
