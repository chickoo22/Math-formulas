package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CalcHistoryDao {
    @Query("SELECT * FROM calc_history ORDER BY timestamp DESC LIMIT 50")
    fun getRecentHistory(): Flow<List<CalcHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(item: CalcHistoryEntity)

    @Query("DELETE FROM calc_history WHERE id = :id")
    suspend fun deleteHistoryItem(id: Long)

    @Query("DELETE FROM calc_history")
    suspend fun clearAllHistory()
}
