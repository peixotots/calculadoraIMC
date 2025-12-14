package com.example.calculadoradeimc.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            Text(text = "IMC: ${m.imc}")
            Text(text = "Classificação: ${m.imcClassification}")
            Text(text = "Peso: ${m.weightKg} kg")
            Text(text = "Altura: ${m.heightCm} cm")
            m.tmb?.let { Text(text = "TMB estimada: $it") }
            m.age?.let { Text(text = "Idade: $it") }
            m.sex?.let { Text(text = "Sexo: $it") }
        }
    }
}