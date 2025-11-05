package com.kukareku.readonline.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val author: String?,
    val uri: String?,       // локальный путь или content://
    val format: String?,    // pdf / epub / txt / fb2
    val addedAt: Long = System.currentTimeMillis()
)
