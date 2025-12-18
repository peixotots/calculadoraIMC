package com.example.calculadoradeimc.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculadoradeimc.data.Measurement
import com.example.calculadoradeimc.data.MeasurementRepository
import com.example.calculadoradeimc.domain.ActivityLevel
import com.example.calculadoradeimc.domain.CalculateHealthMetricsUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MeasurementRepository? = null,
    private val useCase: CalculateHealthMetricsUseCase = CalculateHealthMetricsUseCase()
) : ViewModel() {

    var height by mutableStateOf("")
        private set

    var weight by mutableStateOf("70.0")
        private set

    var age by mutableStateOf("18")
        private set

    var sex by mutableStateOf("")
        private set

    var resultMessage by mutableStateOf("")
        private set

    var textFieldError by mutableStateOf(false)
        private set

    fun onHeightChange(value: String) {
        height = value
    }

    fun onWeightChange(value: String) {
        weight = value
    }

    fun onAgeChange(value: String) {
        val filtered = value.filter { ch -> ch.isDigit() }
        age = if (filtered.length <= 3) filtered else filtered.take(3)
    }

    fun onSexChange(value: String) {
        val s = value.uppercase().take(1)
        sex = if (s.isEmpty() || s in listOf("M", "F")) s else sex
    }

    fun onCalculate() {
        val ageInt = age.toIntOrNull()
        val sexVal = sex.takeIf { it.isNotBlank() }
        val res = useCase.execute(
            height,
            weight,
            age = ageInt,
            sex = sexVal,
            activityLevel = activityLevel
        )

        if (res.isError) {
            textFieldError = true
            resultMessage = res.errorMessage ?: "Erro"
        } else {
            textFieldError = false
            val hr = res.healthResult!!
            val tmbText = hr.tmb?.let { "\nTMB (estimada): ${it}" } ?: ""
            val devineMessage = hr.idealWeightKg?.let { "\nPeso ideal (Devine): $it Kg" }
            val calories = hr.dailyCalories?.let { "\n Calorias diárias (Sugestão) : $it" }
            resultMessage =
                "IMC: ${hr.imc} - ${hr.imcClassification}$tmbText$devineMessage$calories"
        }
    }

    fun onCalculateWithExtras(age: Int, sex: String) {
        val res = useCase.execute(height, weight, age = age, sex = sex)
        if (res.isError) {
            textFieldError = true
            resultMessage = res.errorMessage ?: "Erro"
        } else {
            textFieldError = false
            val hr = res.healthResult!!
            val tmbText = hr.tmb?.let { "\nTMB (estimada): ${it}" } ?: ""
            val devineMessage = hr.idealWeightKg.let { "\nPeso ideal (Devine): ${it} Kg" }
            resultMessage = "IMC: ${hr.imc} - ${hr.imcClassification}$tmbText$devineMessage"
        }
    }

    fun saveCurrentMeasurement(age: Int? = null, sex: String? = null) {
        if (repository == null) {
            return
        }

        val ageToUse = age ?: this.age.toIntOrNull()
        val sexToUse = sex ?: this.sex.takeIf { it.isNotBlank() }

        viewModelScope.launch {
            val res = useCase.execute(height, weight, age = ageToUse, sex = sexToUse)
            if (res.isError || res.healthResult == null) {
                textFieldError = true
                resultMessage = res.errorMessage ?: "Erro ao calcular antes de salvar."
                return@launch
            }

            val hr = res.healthResult
            val weightKg = weight.replace(",", ".").trim().toDoubleOrNull() ?: return@launch
            val heightCm = height.replace(",", ".").trim().toDoubleOrNull() ?: return@launch

            val measurement = Measurement(
                weightKg = weightKg,
                heightCm = heightCm,
                imc = hr.imc,
                imcClassification = hr.imcClassification,
                tmb = hr.tmb,
                sex = sexToUse,
                age = ageToUse,
                idealWeightKg = hr.idealWeightKg,
                dailyCalories = hr.dailyCalories
            )

            repository.insert(measurement)
            resultMessage = "Resultado salvo."
        }
    }

    var activityLevel by mutableStateOf(ActivityLevel.SEDENTARY)
        private set

    fun onActivityLevelChange(level: ActivityLevel) {
        activityLevel = level
    }
}