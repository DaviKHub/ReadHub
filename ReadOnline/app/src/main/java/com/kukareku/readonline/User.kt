package com.kukareku.readonline

import androidx.room.*

@Entity(tableName="users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name="email") val email: String,
    @ColumnInfo(name="password_hash") val passwordHash: String,
    @ColumnInfo(name="display_name") val displayName: String? = null
)
