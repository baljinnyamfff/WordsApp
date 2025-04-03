package com.example.wordsapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsManager(private val context: Context) {

    private val wordVisibilityKey = stringPreferencesKey("word_visibility")

    suspend fun saveWordVisibility(visibility: String) {
        context.dataStore.edit { settings ->
            settings[wordVisibilityKey] = visibility
        }
    }

    val wordVisibilityFlow: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[wordVisibilityKey] ?: "both"
        }
}
