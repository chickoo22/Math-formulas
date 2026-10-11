package com.example.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT formulaId FROM bookmarked_formulas")
    fun getAllBookmarkedIds(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarked_formulas WHERE formulaId = :formulaId")
    suspend fun deleteBookmark(formulaId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarked_formulas WHERE formulaId = :formulaId)")
    suspend fun isBookmarked(formulaId: String): Boolean
}
