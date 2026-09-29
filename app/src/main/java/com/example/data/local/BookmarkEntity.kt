package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "formula_bookmarks")
data class BookmarkEntity(
    @PrimaryKey
    val formulaId: String,
    val bookmarkedAt: Long = System.currentTimeMillis()
)
