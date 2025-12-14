package com.example.calculadoradeimc.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.calculadoradeimc.data.Measurement
import com.example.calculadoradeimc.data.MeasurementRepository

@Composable
fun HistoryScreen(
    repo: MeasurementRepository,
    onNavigateToDetail: (Long) -> Unit,
    onBack: () -> Unit
) {
    val measurementsState = repo.getAll().collectAsState(initial = emptyList())

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)) {
        Button(onClick = onBack) {
            Text("VOLTAR")
        }

        LazyColumn {
            items(measurementsState.value) { m: Measurement ->
                HistoryRow(measurement = m, onClick = { onNavigateToDetail(m.id) })
            }
        }
    }
}

@Composable
private fun HistoryRow(measurement: Measurement, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        Text(text = "IMC: ${measurement.imc} — ${measurement.imcClassification}")
        Text(text = "Peso: ${measurement.weightKg} kg — Altura: ${measurement.heightCm} cm")
    }
}