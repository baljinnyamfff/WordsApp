package com.example.wordsapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordsapp.ui.HomeScreen.WordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: WordViewModel = viewModel(factory = WordViewModel.Factory),
    onNavigateBack: () -> Unit
) {
    val settings = viewModel.settings.collectAsState()
    var selectedOption by remember { mutableStateOf(settings.value) }
    LaunchedEffect(settings) {
        selectedOption = settings.value
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedOption == "both",
                    onClick = { selectedOption = "both" }
                )
                Text("Хоёуланг нь ил харуулна")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedOption == "foreign",
                    onClick = { selectedOption = "foreign" }
                )
                Text("Гадаад үгийг ил харуулна")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedOption == "mongolian",
                    onClick = { selectedOption = "mongolian" }
                )
                Text("Монгол үгийг ил харуулна")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(

            ) {
                Button(
                    onClick = {onNavigateBack()},
                ) {
                    Text("Буцах")
                }
                Button(
                    onClick = {viewModel.updateSettings(selectedOption)},
                    enabled = selectedOption != settings.value
                ) {
                    Text("Хадгалах")
                }
            }
        }
    }
}
