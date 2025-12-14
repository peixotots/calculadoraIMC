package com.example.calculadoradeimc.domain

data class HealthResult(
    val imc: Double,
    val imcClassification: String,
    val tmb: Double? = null // null para quando não houver dados (idade/sexo) para calcular
)