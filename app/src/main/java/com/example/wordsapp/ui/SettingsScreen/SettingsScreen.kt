package com.example.wordsapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    val settings = viewModel.settings.collectAsState(initial = "both")

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
            verticalArrangement = Arrangement.Center
        ) {
            Text("Word Display Settings", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = settings.value == "both",
                    onClick = { viewModel.updateSettings("both") }
                )
                Text("Хоёуланг нь ил харуулна")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = settings.value == "foreign",
                    onClick = { viewModel.updateSettings("foreign") }
                )
                Text("Гадаад үгийг ил харуулна")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = settings.value == "mongolian",
                    onClick = { viewModel.updateSettings("mongolian") }
                )
                Text("Монгол үгийг ил харуулна")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(

            ) {
                Button(
                    onClick = {onNavigateBack},
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Буцах")
                }
                Button(
                    onClick = {viewModel.updateSettings(settings.value)},
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Хадгалах")
                }
            }
        }
    }
}
