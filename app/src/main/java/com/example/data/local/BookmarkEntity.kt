package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_formulas")
data class BookmarkEntity(
    @PrimaryKey
    val formulaId: String,
    val bookmarkedAt: Long = System.currentTimeMillis()
)
