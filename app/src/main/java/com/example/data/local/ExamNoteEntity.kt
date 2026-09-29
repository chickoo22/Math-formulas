package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exam_notes")
data class ExamNoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val category: String,
    val content: String,
    val formulaSnippet: String = "",
    val isPinned: Boolean = false,
    val colorTag: String = "#38BDF8", // Hex color
    val createdAt: Long = System.currentTimeMillis()
)
