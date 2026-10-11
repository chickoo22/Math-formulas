package com.example.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamNoteDao {
    @Query("SELECT * FROM exam_notes ORDER BY isPinned DESC, createdAt DESC")
    fun getAllNotes(): Flow<List<ExamNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: ExamNoteEntity): Long

    @Update
    suspend fun updateNote(note: ExamNoteEntity)

    @Delete
    suspend fun deleteNote(note: ExamNoteEntity)

    @Query("DELETE FROM exam_notes WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("UPDATE exam_notes SET isPinned = NOT isPinned WHERE id = :id")
    suspend fun togglePin(id: Long)

    @Query("SELECT COUNT(*) FROM exam_notes")
    suspend fun getNoteCount(): Int
}
