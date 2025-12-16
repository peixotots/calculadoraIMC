package com.example.calculadoradeimc.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp
import com.example.calculadoradeimc.ui.theme.Blue
import com.example.calculadoradeimc.ui.theme.Red
import com.example.calculadoradeimc.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpScreen(onBack: () -> Unit) {

    Scaffold(
        topBar = {TopAppBar(
            title = {
                Text(text = "Ajuda (Como funciona a calculadora)",
                    fontWeight = FontWeight.Bold)
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Blue,
                titleContentColor = White
            )
        )}
    ) {
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    color = White
                )
                .verticalScroll(rememberScrollState())
        ) {
            Column(
            ){
                Text(
                    text = "Cálculo do IMC",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(20.dp, 20.dp,0.dp,0.dp),
                    color = Blue
                )
            }
            Row(){
                Text(
                    text = "'Obtido a partir do peso e da altura do indivíduo, " +
                            "o IMC também aponta níveis de magreza e obesidade, " +
                            "que são usados para nortear o trabalho de profissionais " +
                            "de saúde e de educadores físicos.\n" +
                            "\n" +
                            "Exemplo: João tem 83 kg e sua altura é 1,75 m" +
                            "\n" +
                            "IMC=peso (kg) / altura (m) x altura (m)" +
                            " \n" +
                            "Altura x altura = 1,75 x 1,75 = 3.0625" +
                            "\n" +
                            "IMC = 83 divididos por 3,0625 = 27,10" +
                            " \n" +
                            "O resultado de 27,10 de IMC indica que João está acima " +
                            "do peso desejado (sobrepeso)"+
                            "\nO IMC é considerado normal entre 18,5 e 24,9 segundo a OMS.",
                    modifier = Modifier.padding(10.dp, 2.dp, 20.dp, 0.dp),
                    textAlign = TextAlign.Justify,
                    color = Color(0xFF3F51B5)
                )
            }

            Row(){
                Text(
                    text = "Cálculo da TMB",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(20.dp, 20.dp,0.dp,0.dp),
                    color = Blue
                )
            }
            Row(){
                Text(
                    text = "A taxa metabólica basal (TMB) " +
                            "corresponde à quantidade mínima de " +
                            "energia que o corpo precisa para funcionar " +
                            "diariamente. Isso inclui também períodos de " +
                            "nenhum esforço, como dormir ou em repouso. ",
                    modifier = Modifier.padding(10.dp, 2.dp, 20.dp, 0.dp),
                    textAlign = TextAlign.Justify,
                    color = Color(0xFF3F51B5)
                )
            }
            Row() {
                Text(
                    text = "Por exemplo: uma mulher que tenha entre " +
                            "18 a 30 anos deve fazer o seguinte cálculo: " +
                            "(14,818 x peso em kg) + 486,6. " +
                            "Já um homem da mesma idade precisa usar: " +
                            "(15,057 x peso em kg) + 692,2.",
                    modifier = Modifier.padding(10.dp, 2.dp, 20.dp, 0.dp),
                    textAlign = TextAlign.Justify,
                    color = Color(0xFF3F51B5)
                )
            }
            Row(){
                Text(
                    text = "Cáculo do Peso Ideal",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(20.dp, 20.dp,0.dp,0.dp),
                    color = Blue
                )
            }
            Row(){
                Text(
                    text = "A Fórmula Devine calcula o seu " +
                            "peso corporal ideal com base apenas " +
                            "na altura, fornecendo uma referência " +
                            "médica para atletas e entusiastas do fitness. " +
                            "Os resultados ajudam a estabelecer metas de peso " +
                            "para treinos e competições."+"\n"+
                            "\nPara homens:" +
                            "Peso corporal ideal (kg) = 50 + 2,3 × (altura em polegadas - 60)\n" +
                            "\n" +
                            "Para mulheres:\n" +
                            "Peso corporal ideal (kg) = 45,5 + 2,3 × (altura em polegadas - 60)",
                    modifier = Modifier.padding(10.dp, 2.dp, 20.dp, 0.dp),
                    textAlign = TextAlign.Justify,
                    color = Color(0xFF3F51B5)
                )
            }
            Row(){
                Text(
                    text = "TMB e as calorias Diárias",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(20.dp, 20.dp,0.dp,0.dp),
                    color = Blue
                )
            }

            Row(){
                Text(
                    text = "A perda de peso ocorre quando o corpo " +
                            "queima mais calorias do que consome. " +
                            "Portanto, se uma pessoa consome menos " +
                            "calorias do que sua TMB, seu corpo começará " +
                            "a queimar reservas de gordura para " +
                            "obter energia e, como resultado, ela perderá peso."+"\n"+
                            "Muitos nutricionistas fazem o cálculo da " +
                            "TMB para fazer uma estimativa da quantidade " +
                            "de calorias que aquele indivíduo deve consumir " +
                            "para manter ou perder peso. ",
                    modifier = Modifier.padding(10.dp, 2.dp, 20.dp, 0.dp),
                    textAlign = TextAlign.Justify,
                    color = Color(0xFF3F51B5))
            }
        }
    }
}

@Preview
@Composable
private fun helpPreview() {
    HelpScreen(onBack = {})

}