package com.example.wordsapp.data

import android.app.Application

class WordsApplication: Application() {
    lateinit var repository: WordRepository

    override fun onCreate() {
        super.onCreate()
        val database = WordDatabase.getDatabase(this)
        repository = OfflineWordRepository(database.wordDao())
    }
}