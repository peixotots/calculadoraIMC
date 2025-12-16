package com.example.calculadoradeimc.domain
enum class HealthStatus { OK, WARNING }
data class HealthResult(
    val imc: Double,
    val imcClassification: String,
    val tmb: Double? = null,// null para quando não houver dados (idade/sexo) para calcular
    val status: HealthStatus,
    val idealWeightKg: Double?,
    val dailyCalories: Double?
)