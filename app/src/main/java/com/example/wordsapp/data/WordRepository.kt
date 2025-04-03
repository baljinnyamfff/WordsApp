package com.example.wordsapp.data

import kotlinx.coroutines.flow.Flow

interface WordRepository {
    val allWords: Flow<List<Word>>
    suspend fun insert(word: Word)
    suspend fun update(word: Word)
    suspend fun delete(word: Word)
}