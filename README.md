# Programação para Dispositivos Móveis - Trabalho 2

## Calculadora de IMC e outras métricas de saúde

### Visão geral 📌
Aplicativo Android desenvolvido em Kotlin com Jetpack Compose que permite calcular o IMC (Índice de Massa Corporal) e outras métricas de saúde, como TMB (Taxa Metabólica Basal), peso ideal (fórmula de Devine) e estimativa de necessidade calórica diária.
O projeto utiliza arquitetura MVVM e armazena o histórico das medições localmente usando Room.

### Funcionalidades ✨
- Cálculo do IMC a partir do peso (kg) e altura (cm) com classificação (Abaixo do peso, Peso Normal, Sobrepeso, Obesidade).
- Cálculo opcional da TMB (fórmula baseada em Mifflin-St Jeor) quando idade e sexo são informados.
- Cálculo do peso ideal pela fórmula de Devine (quando o sexo é fornecido).
- Estimativa de calorias diárias a partir da TMB e de um fator de atividade física (Sedentário, Leve, Moderado, Intenso).
- Salvar medições calculadas em um banco local (Room) e visualizar histórico.
- Tela de ajuda explicando as fórmulas e conceitos.

### Arquitetura 🏛️
O aplicativo foi organizado seguindo o padrão MVVM, separando claramente:

- Interface (UI): telas feitas com Jetpack Compose.
- ViewModel: responsável por gerenciar os estados da tela e intermediar ações do usuário.
- Camada de domínio: concentra toda a lógica de cálculo das métricas de saúde.
- Camada de dados: persistência local usando Room para salvar o histórico de medições.

Essa separação facilita a manutenção, testes e evolução do aplicativo.

### Mapa de código (principais arquivos e responsabilidades) 🗺️
- `app/src/main/java/com/example/calculadoradeimc/MainActivity.kt` — ponto de entrada da UI; cria instâncias do banco, repositório e ViewModel; configura NavHost com as rotas do app.
- `app/src/main/java/com/example/calculadoradeimc/NavRoutes.kt` — rotas de navegação declaradas como sealed class.

Domain
- `domain/CalculateHealthMetricsUseCase.kt` — contém a lógica para:
    - validar entradas (altura/peso),
    - calcular IMC (com arredondamento a 2 casas),
    - classificar IMC,
    - calcular TMB (quando idade e sexo são informados) usando Mifflin-St Jeor,
    - calcular peso ideal pela fórmula de Devine,
    - estimar calorias diárias a partir da TMB e nível de atividade.
- `domain/HealthResult.kt` — model que representa o resultado dos cálculos (IMC, classificação, TMB, peso ideal, calorias, status).
- `domain/ActivityLevel.kt` — enumeração com os níveis de atividade e rótulos usados na UI.

ViewModel
- `viewmodel/HomeViewModel.kt` — mantém estados (height, weight, age, sex, activityLevel, resultMessage, textFieldError), ligações para a UI, executa `CalculateHealthMetricsUseCase` para produzir mensagens e, quando existe `MeasurementRepository`, salva medições.
- `viewmodel/HomeViewModelFactory.kt` — fábrica do ViewModel para prover o `MeasurementRepository`.

UI (Jetpack Compose)
- `view/Home.kt` — tela principal: campos para altura, peso, seleção de sexo, idade, seletor de nível de atividade; botões para calcular, salvar e navegar ao histórico/ajuda; vários Composables auxiliares (SexSelector, HeightCard, CounterCard, InfoCard, etc.).
- `view/HistoryScreen.kt` — lista o histórico de medições vindas de `MeasurementRepository` e permite navegar para detalhes.
- `view/DetailScreen.kt` — exibe os detalhes de uma medição (IMC, classificação, peso, altura, TMB, peso ideal, idade, sexo, calorias).
- `view/HelpScreen.kt` — tela com explicações sobre IMC, TMB, peso ideal e calorias.

Data (Room)
- `data/Measurement.kt` — Entity Room que modela uma medição (id, timestamp, weightKg, heightCm, imc, imcClassification, tmb, sex, age, idealWeightKg, dailyCalories).
- `data/MeasurementDao.kt` — DAO com métodos para inserir, obter todos ordenados por data, obter por id e deletar.
- `data/MeasurementRepository.kt` — wrapper simples sobre o DAO, fornece Flow<List<Measurement>> para a UI.
- `data/AppDatabase.kt` — configuração do Room Database com singleton `getInstance`.

UI Theme
- `ui/theme/Color.kt`, `Theme.kt`, `Type.kt` — definições de cores, tipografia e tema Material3 usados pela aplicação.

### Fórmulas e decisões de implementação 🧮
- IMC: IMC = peso(kg) / (altura(m) ^ 2). Resultado arredondado para 2 casas.
- Classificação de IMC: baseado em intervalos (Abaixo do peso, Peso Normal, Sobrepeso, Obesidade graus 1–3).
- TMB: implementada versão inspirada na equação de Mifflin-St Jeor:
    - Homens: 10 × peso + 6.25 × altura(cm) − 5 × idade + 5
    - Mulheres: 10 × peso + 6.25 × altura(cm) − 5 × idade − 161
    - A TMB é calculada somente se `age` e `sex` forem fornecidos.
- Peso ideal: fórmula de Devine (altura convertida para polegadas):
    - Homens: 50 + 2.3 × (altura(inches) − 60)
    - Mulheres: 45.5 + 2.3 × (altura(inches) − 60)
- Calorias diárias estimadas: TMB × fator de atividade (sedentário=1.2, leve=1.375, moderado=1.55, intenso=1.725).

### Fluxo de uso ▶️
1. Abrir app → Tela inicial (Home).
2. Inserir altura (cm) e peso (kg). Ajustar idade e sexo caso deseje TMB e peso ideal.
3. Selecionar nível de atividade.
4. Clicar em "CALCULAR" para ver IMC, classificação e possíveis estimativas (TMB, peso ideal, calorias).
5. Clicar em "SALVAR" para persistir a medição no histórico.
6. Acessar "HISTÓRICO" para ver medidas salvas; tocar em uma entrada para ver detalhes.
7. Acessar "AJUDA" para ver explicações e fórmulas usadas.

### Tecnologias 🧰
- Kotlin
- Jetpack Compose (UI declarativa)
- AndroidX (Lifecycle, Navigation)
- Room (persistência local)
- Gradle (build)

### Principais dificuldades encontradas ⚠️
- Contato inicial com Kotlin, Android Studio e Jetpack Compose.
- Entendimento da arquitetura MVVM e da separação correta de responsabilidades.
- Gerenciamento de estado na UI com Compose.
- Validação de dados de entrada para evitar valores inválidos ou inconsistentes.

### Integrantes 👥
- Bruna Ribeiro Teodoro
- Tainá Souza Peixoto