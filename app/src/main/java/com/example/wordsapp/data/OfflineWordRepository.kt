package com.example.wordsapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OfflineWordRepository(private val wordDao: WordDao): WordRepository {
    override val allWords: Flow<List<Word>> = wordDao.getAllWords()

    override suspend fun insert(word: Word){
        wordDao.insertWord(word)
    }

    override suspend fun update(word: Word){
        wordDao.updateWord(word)
    }

    override suspend fun delete(word: Word) {
        wordDao.deleteWord(word)
    }
}