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
        sex: String? = null,
        activityLevel: ActivityLevel = ActivityLevel.SEDENTARY
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
        val idealWeight = calculateIdealWeight(hCm, sex)//CRIEI
        val status = when {
            rawImc < 18.5 || rawImc >= 25.0 ->
                HealthStatus.WARNING

            else -> HealthStatus.OK
        }

        val activityLevel = ActivityLevel.SEDENTARY
        val dailyCalories = calculateDailyCalories(tmb, activityLevel)

        val result = HealthResult(
            imc = imc,
            imcClassification = classification,
            tmb = tmb,
            status = status,
            idealWeightKg = idealWeight,
            dailyCalories = dailyCalories
        )
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

    //USA A FORMULA DE DEVINE PARA CALCULAR O PESO IDEAL
    private fun calculateIdealWeight(heightCm: Double, sex: String?): Double? {
        if (sex == null) return null

        val isMale = sex.uppercase().startsWith("M")

        val heightInches = heightCm / 2.54 //vai converter os centimetros para inches

        val idealKg = if (isMale) {
            50.0 + 2.3 * (heightInches - 60.0)
        } else {
            45.5 + 2.3 * (heightInches - 60.0)
        }
        return (round(idealKg * 100) / 100.0)
    }

    //CALCULA O NÍVEL DE ATIVIDADE PELO TMB
    private fun calculateDailyCalories(tmb: Double?, activityLevel: ActivityLevel): Double? {
        if (tmb == null) return null
        val factor = when (activityLevel) {
            ActivityLevel.SEDENTARY -> 1.2
            ActivityLevel.LIGHT -> 1.375
            ActivityLevel.INTENSE -> 1.725
            ActivityLevel.MODERATE -> 1.55
        }

        val raw = tmb * factor
        return (kotlin.math.round(raw))
    }
}