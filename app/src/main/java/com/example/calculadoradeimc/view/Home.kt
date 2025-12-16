package com.example.calculadoradeimc.view

import android.R.attr.enabled
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox


import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ModalWideNavigationRail
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoradeimc.ui.theme.Blue
import com.example.calculadoradeimc.ui.theme.White
import com.example.calculadoradeimc.viewmodel.HomeViewModel
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.type
import com.example.calculadoradeimc.domain.ActivityLevel
import com.example.calculadoradeimc.ui.theme.Red




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(viewModel: HomeViewModel, onNavigateToHistory: () -> Unit, onNavigateToHelp: () -> Unit) {

    val height = viewModel.height
    val weight = viewModel.weight
    val age = viewModel.age
    val sex = viewModel.sex
    val resultMessage = viewModel.resultMessage
    val textFieldError = viewModel.textFieldError

    val selected = viewModel.activityLevel

   // Text(activityLevel.label)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Calculadora de IMC")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue,
                    titleContentColor = White
                )
            )
            Button(
                onClick = (onNavigateToHelp),
                colors = ButtonDefaults.buttonColors(containerColor = White),
                modifier = Modifier.fillMaxWidth()
                    .padding(260.dp, 5.dp, 20.dp, 0.dp)
            ) {
                Text(
                    text = "Ajuda",
                    fontSize = 16.sp,
                    color = Blue,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { paddingValues ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    color = White
                )
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Altura (cm)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(20.dp, 30.dp, 0.dp, 0.dp)
                )

                Text(
                    text = "Peso (Kg)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(0.dp, 30.dp, 20.dp, 0.dp),
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = height,
                    onValueChange = {
                        if (it.length <= 3) {
                            viewModel.onHeightChange(it.filter { ch -> ch.isDigit() })
                        }
                    },
                    label = {
                        Text(text = "Ex: 165")
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .padding(20.dp, 0.dp, 0.dp, 20.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = White,
                        focusedContainerColor = White,
                        errorContainerColor = White,
                        focusedLabelColor = Blue,
                        focusedIndicatorColor = Blue,
                        cursorColor = Blue,
                    ),
                    isError = textFieldError
                )

                OutlinedTextField(
                    value = weight,
                    onValueChange = {
                        if (it.length <= 7) {
                            viewModel.onWeightChange(it.filter { ch -> ch.isDigit() || ch == ',' || ch == '.' })
                        }
                    },
                    label = {
                        Text(text = "Ex: 70.50")
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(20.dp, 0.dp, 20.dp, 20.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = White,
                        focusedContainerColor = White,
                        errorContainerColor = White,
                        focusedLabelColor = Blue,
                        focusedIndicatorColor = Blue,
                        cursorColor = Blue,
                    ),
                    isError = textFieldError
                )
            }

            // Campos idade e sexo ligados ao ViewModel

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Idade (anos)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(20.dp, 20.dp, 0.dp, 0.dp)
                )

                Text(
                    text = "Sexo (M/F)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(0.dp, 20.dp, 20.dp, 0.dp),
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = age,
                    onValueChange = { viewModel.onAgeChange(it) },
                    label = { Text("Ex: 18") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .padding(20.dp, 0.dp, 0.dp, 20.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = White,
                        focusedContainerColor = White,
                        focusedIndicatorColor = Blue,
                        cursorColor = Blue,
                    )
                )

                OutlinedTextField(
                    value = sex,
                    onValueChange = { viewModel.onSexChange(it) },
                    label = { Text("Ex: F") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(20.dp, 0.dp, 20.dp, 20.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = White,
                        focusedContainerColor = White,
                        focusedIndicatorColor = Blue,
                        cursorColor = Blue,
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "Nível de atividade Diária (escolha)",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(20.dp, 20.dp, 0.dp, 0.dp)
                )
            }
            // RadioButton de atividade diária

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 0.dp, vertical = 6.dp),

                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ActivityLevel.values().forEach { level ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selected == level,
                            onClick = { viewModel.onActivityLevelChange(level) },

                        )
                        Text(
                            level.label,
                            modifier = Modifier.padding(start= 0.dp)
                            )

                    }
                }
            }

            // Botão CALCULAR (ViewModel decide usar extras se disponíveis)
            Button(
                onClick = { viewModel.onCalculate() },
                colors = ButtonDefaults.buttonColors(containerColor = Blue),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(50.dp, 5.dp)
            ) {
                Text(
                    text = "CALCULAR",
                    fontSize = 16.sp,
                    color = White,
                    fontWeight = FontWeight.Bold
                )
            }

            // Botão SALVAR (ViewModel usa os valores expostos age/sex)
            Button(
                onClick = { viewModel.saveCurrentMeasurement() },
                colors = ButtonDefaults.buttonColors(containerColor = Blue),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp)
                    .padding(50.dp, 10.dp)
            ) {
                Text(
                    text = "SALVAR",
                    fontSize = 16.sp,
                    color = White,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = onNavigateToHistory,
                colors = ButtonDefaults.buttonColors(containerColor = Blue),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(50.dp, 6.dp)
            ) {
                Text(text = "HISTÓRICO",
                    color = White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            //ALTERADO
            val isWarning = !resultMessage.contains("Peso Normal", ignoreCase = true)
            val resultColor = if(isWarning){
                Color(0xFFC62828)
            } else {
                Color(0xFF2196F3)
            }
            //Fim do alterado
            Text(
                text = resultMessage,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = resultColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }

    }

}







@Preview
@Composable
private fun HomePreview() {
    val vm = remember { HomeViewModel(null) }
    Home(viewModel = vm, onNavigateToHistory = {}, onNavigateToHelp = {})
}