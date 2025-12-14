package com.example.calculadoradeimc.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MeasurementDao {

    @Insert
    suspend fun insert(measurement: Measurement): Long

    @Query("SELECT * FROM measurements ORDER BY timestamp DESC")
    fun getAllOrderedByDate(): Flow<List<Measurement>>

    @Query("SELECT * FROM measurements WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): Measurement?

    @Delete
    suspend fun delete(measurement: Measurement)

    @Query("DELETE FROM measurements WHERE id = :id")
    suspend fun deleteById(id: Long)
}