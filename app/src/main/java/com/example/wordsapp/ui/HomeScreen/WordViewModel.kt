package com.example.wordsapp.ui.HomeScreen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import com.example.wordsapp.data.Word
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.wordsapp.data.SettingsManager
import com.example.wordsapp.data.WordRepository
import com.example.wordsapp.data.WordsApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WordViewModel(
    application: Application,
    private val repository: WordRepository
): ViewModel() {
    private val settingsManager = SettingsManager(application)

    private val _allWords = MutableStateFlow<List<Word>>(emptyList())
    val allWords: StateFlow<List<Word>> = _allWords

    private val _settings = MutableStateFlow("both")
    val settings: StateFlow<String> = _settings

    init {
        viewModelScope.launch {
            repository.allWords.collect { words ->
                _allWords.value = words
            }
        }

        viewModelScope.launch {
            settingsManager.wordVisibilityFlow.collect { visibility ->
                _settings.value = visibility
            }
        }
    }
    fun insertWord(word: Word) {
        viewModelScope.launch {
            repository.insert(word)
        }
    }

    fun updateWord(word: Word) {
        viewModelScope.launch {
            repository.update(word)
        }
    }

    fun deleteWord(word: Word) {
        viewModelScope.launch {
            repository.delete(word)
        }
    }

    fun updateSettings(visibility: String) {
        viewModelScope.launch {
            settingsManager.saveWordVisibility(visibility)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as WordsApplication
                val wordRepository = application.repository
                WordViewModel(application, wordRepository)
            }
        }
    }
}