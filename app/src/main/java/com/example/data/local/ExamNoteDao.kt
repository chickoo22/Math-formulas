package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamNoteDao {
    @Query("SELECT * FROM exam_notes ORDER BY isPinned DESC, createdAt DESC")
    fun getAllNotes(): Flow<List<ExamNoteEntity>>

    @Query("SELECT * FROM exam_notes WHERE category = :category ORDER BY isPinned DESC, createdAt DESC")
    fun getNotesByCategory(category: String): Flow<List<ExamNoteEntity>>

    @Query("SELECT * FROM exam_notes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' ORDER BY isPinned DESC, createdAt DESC")
    fun searchNotes(query: String): Flow<List<ExamNoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: ExamNoteEntity): Long

    @Update
    suspend fun updateNote(note: ExamNoteEntity)

    @Query("UPDATE exam_notes SET isPinned = NOT isPinned WHERE id = :id")
    suspend fun togglePin(id: Long)

    @Query("DELETE FROM exam_notes WHERE id = :id")
    suspend fun deleteNote(id: Long)

    @Query("SELECT COUNT(*) FROM exam_notes")
    suspend fun getCount(): Int
}
