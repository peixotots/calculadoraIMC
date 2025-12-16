package com.example.calculadoradeimc.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "measurements")
data class Measurement(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val timestamp: Long = System.currentTimeMillis(),
    val weightKg: Double,
    val heightCm: Double,
    val imc: Double,
    val imcClassification: String,
    val tmb: Double? = null,
    val sex: String? = null,
    val age: Int? = null,
    val idealWeightKg: Double?,
    val dailyCalories: Double?
)