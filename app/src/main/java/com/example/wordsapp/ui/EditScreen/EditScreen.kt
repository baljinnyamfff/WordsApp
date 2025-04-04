package com.example.wordsapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordsapp.data.Word
import com.example.wordsapp.ui.HomeScreen.WordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    wordToEdit: Word? = null,
    viewModel: WordViewModel = viewModel(factory = WordViewModel.Factory),
    onNavigateBack: () -> Unit
) {
    var foreignWord by remember { mutableStateOf(wordToEdit?.english ?: "") }
    var mongolianWord by remember { mutableStateOf(wordToEdit?.mongolian ?: "") }
    val isEditing = wordToEdit != null

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "Үг шинэчлэх" else "Үг нэмэх") }
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
            OutlinedTextField(
                value = foreignWord,
                onValueChange = { foreignWord = it },
                label = { Text("Гадаад үг") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = mongolianWord,
                onValueChange = { mongolianWord = it },
                label = { Text("Монгол үг") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (foreignWord.isNotBlank() && mongolianWord.isNotBlank()) {
                        val word = Word(
                            id = wordToEdit?.id ?: 0,
                            english = foreignWord,
                            mongolian = mongolianWord
                        )
                        if (isEditing) {
                            viewModel.updateWord(word)
                        } else {
                            viewModel.insertWord(word)
                        }
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = foreignWord.isNotBlank() && mongolianWord.isNotBlank()
            ) {
                Text(if (isEditing) "ШИНЭЧЛЭХ" else "НЭМЭХ")
            }
        }
    }
}