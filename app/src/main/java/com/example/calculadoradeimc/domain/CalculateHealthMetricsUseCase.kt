package com.example.calculadoradeimc.domain

import kotlin.math.pow
import kotlin.math.round

class CalculateHealthMetricsUseCase {

    data class CalculationResult(
        val healthResult: HealthResult? = null,
        val isError: Boolean = false,
        val errorMessage: String? = null
    )

    fun execute(
        heightCmStr: String,
        weightKgStr: String,
        age: Int? = null,
        sex: String? = null
    ): CalculationResult {
        val heightCm = heightCmStr.replace(",", ".").trim()
        val weightKg = weightKgStr.replace(",", ".").trim()

        if (heightCm.isEmpty() || weightKg.isEmpty()) {
            return CalculationResult(isError = true, errorMessage = "Preencha altura e peso.")
        }

        val hCm = heightCm.toDoubleOrNull()
        val wKg = weightKg.toDoubleOrNull()

        if (hCm == null || wKg == null) {
            return CalculationResult(isError = true, errorMessage = "Valores inválidos.")
        }

        if (hCm <= 0 || wKg <= 0) {
            return CalculationResult(
                isError = true,
                errorMessage = "Valores devem ser maiores que zero."
            )
        }

        val heightM = hCm / 100.0
        val rawImc = wKg / heightM.pow(2.0)
        val imc = (round(rawImc * 100) / 100.0) // 2 casas

        val classification = classifyImc(rawImc)

        // calcula TMB somente se age e sex forem informados
        val tmb: Double? = if (age != null && sex != null) {
            val rawTmb = if (sex.uppercase().startsWith("M")) {
                10.0 * wKg + 6.25 * hCm - 5.0 * age + 5.0
            } else {
                // assume feminino caso contrário
                10.0 * wKg + 6.25 * hCm - 5.0 * age - 161.0
            }
            (round(rawTmb * 100) / 100.0)
        } else {
            null
        }

        val result = HealthResult(imc = imc, imcClassification = classification, tmb = tmb)
        return CalculationResult(healthResult = result)
    }

    private fun classifyImc(imc: Double): String {
        return when {
            imc < 18.5 -> "Abaixo do peso."
            imc < 25.0 -> "Peso Normal."
            imc < 30.0 -> "Sobrepeso."
            imc < 35.0 -> "Obesidade (Grau 1)."
            imc < 40.0 -> "Obesidade Severa (Grau 2)."
            else -> "Obesidade Morbida (Grau 3)."
        }
    }
}