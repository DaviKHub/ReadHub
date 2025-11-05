package com.kukareku.readonline

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val coverUrl: String = "",
    val rating: Float = 0f,
    val genre: String = "",
    val price: String = "",
    val isNew: Boolean = false,
    val progress: Int = 0,
    val lastRead: String = ""
)
