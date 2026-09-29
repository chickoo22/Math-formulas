package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT formulaId FROM formula_bookmarks")
    fun getAllBookmarkedIds(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM formula_bookmarks WHERE formulaId = :formulaId")
    suspend fun removeBookmark(formulaId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM formula_bookmarks WHERE formulaId = :formulaId)")
    suspend fun isBookmarked(formulaId: String): Boolean
}
