package com.kukareku.readonline.data.repository

import android.content.Context
import com.kukareku.readonline.data.db.AppDatabase
import com.kukareku.readonline.data.db.Book
import com.kukareku.readonline.data.db.User

class Repository(private val context: Context) {
    private val db = AppDatabase.getInstance(context)
    private val userDao = db.userDao()
    private val bookDao = db.bookDao()

    suspend fun createUser(user: User): Long = userDao.insert(user)
    suspend fun findUserByEmail(email: String): User? = userDao.findByEmail(email)
    suspend fun getUserById(id: Long): User? = userDao.findById(id)

    suspend fun insertBook(book: Book): Long = bookDao.insert(book)
    suspend fun allBooks(): List<Book> = bookDao.allBooks()
    suspend fun searchBooks(q: String): List<Book> = bookDao.search("%$q%")
    suspend fun deleteBook(id: Long) = bookDao.deleteById(id)
}
