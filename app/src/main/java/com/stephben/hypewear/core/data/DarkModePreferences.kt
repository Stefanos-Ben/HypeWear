package com.stephben.hypewear.core.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.dataStore by preferencesDataStore("user_prefs")

private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode_enabled")

class DarkModePreferences(private val context: Context) {

    val darkModeEnabled: Flow<Boolean> =
        context.dataStore.data.map { it[DARK_MODE_KEY] ?: false }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DARK_MODE_KEY] = enabled
        }
    }
}