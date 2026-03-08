package com.example.proyectolumier.data.datastore

/**
 * @author: David Muñoz Flores
 * @author: Marco Lodeiro Ruiz De La Hermosa
 */

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Extensión para crear el DataStore como singleton
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "lumier_preferences")

class LumierPreferencesRepository(private val context: Context) {

    companion object {
        // Claves del DataStore
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        val DARK_MODE_SET_KEY = booleanPreferencesKey("dark_mode_set")
        val LAST_USER_KEY = stringPreferencesKey("last_user_email")
    }


    val darkModeFlow: Flow<Boolean?> = context.dataStore.data.map { preferences ->
        val isSet = preferences[DARK_MODE_SET_KEY] ?: false
        if (isSet) preferences[DARK_MODE_KEY] else null
    }


    suspend fun saveDarkMode(isDark: Boolean?) {
        context.dataStore.edit { preferences ->
            if (isDark == null) {
                preferences[DARK_MODE_SET_KEY] = false
                preferences.remove(DARK_MODE_KEY)
            } else {
                preferences[DARK_MODE_SET_KEY] = true
                preferences[DARK_MODE_KEY] = isDark
            }
        }
    }


    suspend fun saveLastUser(email: String) {
        context.dataStore.edit { preferences ->
            preferences[LAST_USER_KEY] = email
        }
    }

    val lastUserFlow: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[LAST_USER_KEY] ?: ""
    }
}
