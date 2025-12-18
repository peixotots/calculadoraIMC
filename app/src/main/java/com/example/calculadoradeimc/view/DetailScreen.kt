package com.example.calculadoradeimc.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoradeimc.data.MeasurementRepository

@Composable
fun DetailScreen(
    repo: MeasurementRepository,
    id: Long,
    onBack: () -> Unit
) {
    val measurementState = produceState(
        initialValue = null as? com.example.calculadoradeimc.data.Measurement?,
        key1 = id
    ) {
        value = repo.getById(id)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Button(onClick = onBack) {
            Text("VOLTAR")
        }

        val m = measurementState.value
        if (m == null) {
            Text("Carregando ou não encontrado...")
        } else {

            val isWarning = !m.imcClassification.contains("Peso Normal", ignoreCase = true)
            val classificationColor = if (isWarning) {
                Color(0xFFC62828)
            } else {
                Color(0xFF2196F3)
            }

            val resultStyle = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            val resultModifier = Modifier
                .padding(20.dp, 5.dp, 0.dp, 0.dp)

            Text(
                text = "IMC: ${m.imc}",
                style = resultStyle,
                modifier = resultModifier
            )
            Text(
                text = "Classificação: ${m.imcClassification}",
                color = classificationColor,
                style = resultStyle,
                modifier = resultModifier
            ) // linha alterada
            Text(
                text = "Peso: ${m.weightKg} kg",
                style = resultStyle,
                modifier = resultModifier
            )
            Text(
                text = "Altura: ${m.heightCm} cm",
                style = resultStyle,
                modifier = resultModifier
            )
            m.idealWeightKg.let {
                Text(
                    text = "Peso ideal (Devine): $it Kg",
                    style = resultStyle,
                    modifier = resultModifier
                )
            }
            m.tmb?.let {
                Text(
                    text = "TMB estimada: $it",
                    style = resultStyle,
                    modifier = resultModifier
                )
            }
            m.age?.let {
                Text(
                    text = "Idade: $it",
                    style = resultStyle,
                    modifier = resultModifier
                )
            }
            m.sex?.let {
                Text(
                    text = "Sexo: $it",
                    style = resultStyle,
                    modifier = resultModifier
                )
            }
            m.dailyCalories?.let {
                Text(
                    text = "Calorias Diárias Estimadas: $it",
                    style = resultStyle,
                    modifier = resultModifier
                )
            }
        }
    }
}
