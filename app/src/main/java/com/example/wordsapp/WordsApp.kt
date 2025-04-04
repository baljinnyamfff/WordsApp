package com.example.wordsapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.wordsapp.ui.EditScreen
import com.example.wordsapp.ui.HomeScreen
import com.example.wordsapp.ui.HomeScreen.WordViewModel
import com.example.wordsapp.ui.SettingsScreen
import com.example.wordsapp.ui.navigation.AddEditScreenDestination
import com.example.wordsapp.ui.navigation.HomeScreenDestination
import com.example.wordsapp.ui.navigation.SettingsScreenDestination

@Composable
fun WordsApp() {
    val navController = rememberNavController()
    val viewModel: WordViewModel = viewModel(factory = WordViewModel.Factory)
    Scaffold() { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = HomeScreenDestination.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(HomeScreenDestination.route){
                HomeScreen(
                    viewModel = viewModel,
                    onNavigateToAddEdit = { word ->
                        navController.navigate(AddEditScreenDestination.route.replace("{wordId}", word?.id.toString()))
                    },
                    onNavigateToSettings = {
                        navController.navigate(SettingsScreenDestination.route)
                    }
                )
            }
            composable(AddEditScreenDestination.route){ backStackEntry ->
                val wordId = backStackEntry.arguments?.getString("wordId")?.toIntOrNull()
                val words by viewModel.allWords.collectAsState()
                val word = words.find { it.id == wordId }
                EditScreen(
                    wordToEdit = word,
                    viewModel = viewModel,
                    onNavigateBack = {navController.popBackStack()}
                )
            }
            composable(SettingsScreenDestination.route){
                SettingsScreen(
                    viewModel = viewModel,
                    onNavigateBack = {navController.popBackStack()}
                )
            }
        }
    }
}