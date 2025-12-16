package com.example.calculadoradeimc.data

import kotlinx.coroutines.flow.Flow

class MeasurementRepository(private val dao: MeasurementDao) {

    fun getAll(): Flow<List<Measurement>> = dao.getAllOrderedByDate()

    suspend fun getById(id: Long): Measurement? = dao.getById(id)

    suspend fun insert(measurement: Measurement): Long = dao.insert(measurement)

    suspend fun delete(measurement: Measurement) = dao.delete(measurement)

    suspend fun deleteById(id: Long) = dao.deleteById(id)
}