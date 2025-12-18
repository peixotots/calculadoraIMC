package com.example.calculadoradeimc.view

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoradeimc.domain.ActivityLevel
import com.example.calculadoradeimc.ui.theme.Blue
import com.example.calculadoradeimc.ui.theme.Blue40
import com.example.calculadoradeimc.ui.theme.GrayBlue
import com.example.calculadoradeimc.ui.theme.White
import com.example.calculadoradeimc.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(viewModel: HomeViewModel, onNavigateToHistory: () -> Unit, onNavigateToHelp: () -> Unit) {

    val height = viewModel.height
    val age = viewModel.age
    val sex = viewModel.sex
    val resultMessage = viewModel.resultMessage

    val selected = viewModel.activityLevel

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Calculadora de IMC")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Blue,
                    titleContentColor = White
                ),
                actions = {
                    Button(
                        onClick = onNavigateToHelp,
                        colors = ButtonDefaults.buttonColors(containerColor = White),
                    ) {
                        Text(
                            text = "Ajuda",
                            fontSize = 16.sp,
                            color = Blue,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            )
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
                modifier = Modifier.padding(25.dp, 10.dp, 0.dp, 0.dp),
            ) {
                Text(
                    text = "Selecione:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Blue
                )
            }
            SexSelector(
                selectedSex = sex,
                onSexSelected = { viewModel.onSexChange(it) },
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            )

            Row(
                modifier = Modifier.padding(20.dp, 10.dp, 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                HeightCard(
                    height = height,
                    onHeightChange = { new -> viewModel.onHeightChange(new) },
                    modifier = Modifier
                        .weight(1f)
                        .height(320.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CounterCard(
                        title = "Peso",
                        value = String.format(
                            "%.1f",
                            viewModel.weight.replace(",", ".").toFloatOrNull() ?: 70f
                        ),
                        onIncrement = {
                            val current = viewModel.weight.replace(",", ".").toFloatOrNull() ?: 70f
                            val newValue = current + 0.5f   // ou 1f se quiser
                            viewModel.onWeightChange(String.format("%.1f", newValue))
                        },
                        onDecrement = {
                            val current = viewModel.weight.replace(",", ".").toFloatOrNull() ?: 70f
                            val newValue = (current - 0.5f).coerceAtLeast(0f)
                            viewModel.onWeightChange(String.format("%.1f", newValue))
                        }
                    )

                    CounterCard(
                        title = "Idade",
                        value = (viewModel.age.toIntOrNull() ?: 18).toString(),
                        onIncrement = {
                            viewModel.onAgeChange(
                                ((age.toIntOrNull() ?: 0) + 1).toString()
                            )
                        },
                        onDecrement = {
                            viewModel.onAgeChange(
                                ((age.toIntOrNull() ?: 0) - 1).coerceAtLeast(0).toString()
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.padding(20.dp, 10.dp, 20.dp)

            ) {

            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Nível de atividade Diária:",
                    color = Blue,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(20.dp, 20.dp, 0.dp, 0.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 0.dp, vertical = 6.dp),

                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ActivityLevel.values().forEach { level ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(
                            selected = selected == level,
                            onClick = { viewModel.onActivityLevelChange(level) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Blue,
                                unselectedColor = Blue40,
                                disabledSelectedColor = Blue,
                                disabledUnselectedColor = Blue40
                            )

                        )
                        Text(
                            level.label,
                            modifier = Modifier.padding(start = 0.dp),
                            color = Blue
                        )

                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Button(
                    onClick = { viewModel.onCalculate() },
                    colors = ButtonDefaults.buttonColors(containerColor = Blue),
                    modifier = Modifier.padding(20.dp, 0.dp, 0.dp, 0.dp)

                ) {
                    Text(
                        text = "CALCULAR",
                        fontSize = 12.sp,
                        color = White,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                }

                Button(
                    onClick = { viewModel.saveCurrentMeasurement() },
                    colors = ButtonDefaults.buttonColors(containerColor = Blue),
                    modifier = Modifier.padding(12.dp, 0.dp, 0.dp, 0.dp)

                ) {
                    Text(
                        text = "SALVAR",
                        fontSize = 12.sp,
                        color = White,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

                Button(
                    onClick = onNavigateToHistory,
                    colors = ButtonDefaults.buttonColors(containerColor = Blue),
                    modifier = Modifier.padding(12.dp, 0.dp, 20.dp, 0.dp)


                ) {
                    Text(
                        text = "HISTÓRICO",
                        color = White,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

            }

            ResultCard(
                resultMessage = resultMessage,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            )
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoCard(
    title: String,
    text: String,
    onTextChange: (String) -> Unit,
    isError: Boolean,
    placeholder: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = GrayBlue,
            contentColor = White,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        shape = RoundedCornerShape(16.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = text,
                onValueChange = onTextChange,
                label = {
                    Text(text = placeholder)
                },
                modifier = Modifier
                    .fillMaxWidth(),
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
                isError = isError

            )
        }
    }

}

@Composable
fun CounterCard(
    title: String,
    value: String,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Blue,
            contentColor = White
        ),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = value,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Button(
                    onClick = onDecrement,
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,
                        contentColor = Blue
                    )
                ) {
                    Text(
                        text = "-",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )


                }
                Button(
                    onClick = onIncrement,
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White,
                        contentColor = Blue
                    )
                ) {
                    Text(
                        text = "+",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun SexSelector(
    selectedSex: String,
    onSexSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SexButton(
            label = "Masculino",
            selected = selectedSex == "M",
            onClick = { onSexSelected("M") },
            modifier = Modifier.weight(1f)
        )

        SexButton(
            label = "Feminino",
            selected = selectedSex == "F",
            onClick = { onSexSelected("F") },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun SexButton(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Blue else Color.Transparent,
            contentColor = if (selected) White else Blue,
        ),
        border = if (selected) null else ButtonDefaults.outlinedButtonBorder
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun HeightCard(
    height: String,
    onHeightChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val heightValue = height.toFloatOrNull() ?: 170f

    Card(
        modifier = modifier.fillMaxWidth(0.7f),
        colors = CardDefaults.cardColors(
            containerColor = White,
            contentColor = Blue
        ),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Altura",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Blue
            )

            Spacer(Modifier.height(16.dp))

            Slider(
                value = heightValue,
                onValueChange = { newValue ->
                    onHeightChange(newValue.toInt().toString())
                },
                valueRange = 120f..220f,          // faixa de altura em cm
                steps = 220 - 120 - 1,           // valores inteiros
                modifier = Modifier
                    .height(150.dp)
                    .width(400.dp)
                    // altura visível do slider
                    .rotate(-90f),
                colors = SliderDefaults.colors(
                    thumbColor = Blue,
                    activeTrackColor = White,
                    inactiveTrackColor = White,

                    )
            )

            Spacer(Modifier.height(30.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                Text(
                    text = heightValue.toInt().toString(),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Blue
                )
                Text(
                    text = " cm",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 4.dp, 10.dp, 0.dp, 2.dp),
                    color = Blue
                )
            }
        }
    }
}

@Composable
fun ResultCard(
    resultMessage: String,
    modifier: Modifier = Modifier
) {
    val isWarning = !resultMessage.contains("Peso Normal", ignoreCase = true)
    val resultColor = if (isWarning) {
        Color(0xFFC62828)
    } else {
        Color(0xFF63B654)
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = resultColor
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = resultMessage,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}


@Preview
@Composable
private fun HomePreview() {
    val vm = remember { HomeViewModel(null) }
    Home(viewModel = vm, onNavigateToHistory = {}, onNavigateToHelp = {})
}