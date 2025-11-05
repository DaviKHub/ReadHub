package com.kukareku.readonline.data.db

import androidx.room.*

@Dao
interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: Book)

    @Query("SELECT * FROM books ORDER BY addedAt DESC")
    suspend fun getAll(): List<Book>

    @Query("SELECT * FROM books WHERE title LIKE :query OR author LIKE :query")
    suspend fun search(query: String): List<Book>

    @Delete
    suspend fun delete(book: Book)
}
