package com.example.wordsapp.ui

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordsapp.data.Word
import com.example.wordsapp.ui.HomeScreen.WordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: WordViewModel = viewModel(factory = WordViewModel.Factory),
    onNavigateToAddEdit: (Word?) -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val words by viewModel.allWords.collectAsState(initial = emptyList())
    var currentIndex by remember { mutableStateOf(0) }
    val settings by viewModel.settings.collectAsState(initial = "both")
    var showDeleteDialog by remember { mutableStateOf(false)}

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Картны апп") },
                actions = {
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { padding ->
        if(showDeleteDialog){
            AlertDialog(
                onDismissRequest = {showDeleteDialog = false},
                title = {
                    Text("Үг устгах")
                },
                text = {
                    Text("Та энэ үгийг устгаж байгаадаа итгэлтэй байна уу?")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.deleteWord(words[currentIndex])
                            showDeleteDialog = false
                            if(currentIndex > 0) { currentIndex-- }
                        }
                    ) {
                        Text("Тийм")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDeleteDialog = false }
                    ) {
                        Text("Үгүй")
                    }
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (words.isEmpty()) {
                Text("Одоогоор үг алга")
                Button(
                    shape = RectangleShape,
                    onClick = { onNavigateToAddEdit(null)}
                ) {
                    Text("НЭМЭХ")
                }
            } else {
                val word = words[currentIndex]
                WordDisplay(word, { onNavigateToAddEdit(word) }, {newSetting -> viewModel.updateSettings( newSetting )} ,settings )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Button(
                        shape = RectangleShape,
                        onClick = { onNavigateToAddEdit(null)}
                    ) {
                        Text("НЭМЭХ")
                    }
                    Button(
                        shape = RectangleShape,
                        onClick = {onNavigateToAddEdit(word)}
                    ) {
                        Text("ШИНЭЧЛЭХ")
                    }
                    Button(
                        shape = RectangleShape,
                        onClick = {showDeleteDialog = true}
                    ) {
                        Text("УСТГАХ")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Button(
                        shape = RectangleShape,
                        onClick = { if (currentIndex > 0) currentIndex-- },
                        enabled = currentIndex > 0
                    ) {
                        Text("ӨМНӨХ")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        shape = RectangleShape,
                        onClick = { if (currentIndex < words.size - 1) currentIndex++ },
                        enabled = currentIndex < words.size - 1
                    ) {
                        Text("ДАРААХ")
                    }
                }
            }
        }
    }
}

@Composable
fun WordDisplay(word: Word, onEdit: () -> Unit, onReveal: (String) -> Unit, settings: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
    ) {
        val displayedEnglish = when (settings) {
            "mongolian" -> ""
            else -> word.english
        }

        val displayedMongolian = when (settings) {
            "foreign" -> ""
            else -> word.mongolian
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = displayedEnglish,
                style = MaterialTheme.typography.headlineLarge,
                color = androidx.compose.ui.graphics.Color.Black,
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                onReveal("Both")
                            },
                            onLongPress = { onEdit() }
                        )
                    }
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(color = androidx.compose.ui.graphics.Color.White)
            )
            Text(
                text = displayedMongolian,
                style = MaterialTheme.typography.headlineMedium,
                color = androidx.compose.ui.graphics.Color.Black,
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {
                                onReveal("Both")
                            },
                            onLongPress = { onEdit() }
                        )
                    }
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(color = androidx.compose.ui.graphics.Color.White)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
}