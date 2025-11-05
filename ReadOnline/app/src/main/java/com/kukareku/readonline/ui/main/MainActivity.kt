package com.kukareku.readonline.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.kukareku.readonline.R
import com.kukareku.readonline.data.db.AppDatabase
import com.kukareku.readonline.data.db.Book
import com.kukareku.readonline.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private val db by lazy { AppDatabase.get(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        // 🔹 Настройка Toolbar и Drawer
        setSupportActionBar(b.toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            b.drawerLayout,
            b.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        b.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        b.navView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    Snackbar.make(b.root, "Главная", Snackbar.LENGTH_SHORT).show()
                }
                R.id.nav_profile -> {
                    Snackbar.make(b.root, "Профиль", Snackbar.LENGTH_SHORT).show()
                }
                R.id.nav_logout -> {
                    Snackbar.make(b.root, "Выход", Snackbar.LENGTH_SHORT).show()
                }
            }
            b.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // 🔹 FAB: выбор источника книги
        b.fabAddBook.setOnClickListener {
            showAddBookDialog()
        }

        // 🔹 Загрузка книг
        loadLibrary()
    }

    // ------------------------------------------------------------------------
    // 📖 Диалог выбора источника книги
    // ------------------------------------------------------------------------
    private fun showAddBookDialog() {
        val options = arrayOf("Из интернета", "Из памяти устройства")
        MaterialAlertDialogBuilder(this)
            .setTitle("Добавить книгу")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> openOnlineSearch()
                    1 -> openFilePicker()
                }
            }
            .show()
    }

    // 🔹 Открыть экран поиска онлайн
    private fun openOnlineSearch() {
        startActivity(Intent(this, SearchResultsActivity::class.java))
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
    }

    // 🔹 Открыть файловый менеджер для выбора книги
    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
            putExtra(
                Intent.EXTRA_MIME_TYPES,
                arrayOf("application/pdf", "application/epub+zip", "text/plain")
            )
        }
        startActivityForResult(intent, 100)
    }

    // ------------------------------------------------------------------------
    // 🧱 Загрузка сохранённых книг из Room
    // ------------------------------------------------------------------------
    private fun loadLibrary() {
        lifecycleScope.launch {
            val books = withContext(Dispatchers.IO) { db.bookDao().getAll() }
            if (books.isEmpty()) {
                Snackbar.make(b.root, "Библиотека пуста", Snackbar.LENGTH_SHORT).show()
            } else {
                // Здесь подключи адаптер для b.libraryRecyclerView
                // Например:
                // b.libraryRecyclerView.adapter = LibraryAdapter(books)
            }
        }
    }

    // ------------------------------------------------------------------------
    // 📂 Обработка выбранного файла
    // ------------------------------------------------------------------------
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val uri: Uri = data?.data ?: return
            val title = uri.lastPathSegment ?: "Книга"
            lifecycleScope.launch {
                val book = Book(
                    title = title,
                    author = "Неизвестен",
                    uri = uri.toString(),
                    format = guessFormat(uri.toString())
                )
                withContext(Dispatchers.IO) {
                    db.bookDao().insert(book)
                }
                Snackbar.make(b.root, "Книга добавлена: $title", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    // ------------------------------------------------------------------------
    // 🔎 Определение формата книги по расширению
    // ------------------------------------------------------------------------
    private fun guessFormat(path: String): String = when {
        path.endsWith(".pdf", true) -> "pdf"
        path.endsWith(".epub", true) -> "epub"
        path.endsWith(".fb2", true) -> "fb2"
        else -> "txt"
    }

    override fun onBackPressed() {
        if (b.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            b.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressedDispatcher.onBackPressed()
        }
    }
}
