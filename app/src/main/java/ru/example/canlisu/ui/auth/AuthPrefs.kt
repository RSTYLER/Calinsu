package ru.example.canlisu.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("auth_prefs")

object AuthPrefs {
    private val KEY_LOGGED_IN = booleanPreferencesKey("logged_in")
    private val KEY_EMAIL = stringPreferencesKey("email")

    fun isLoggedIn(context: Context) =
        context.dataStore.data.map { it[KEY_LOGGED_IN] ?: false }

    suspend fun setLoggedIn(context: Context, value: Boolean) {
        context.dataStore.edit { it[KEY_LOGGED_IN] = value }
    }

    fun savedEmail(context: Context) =
        context.dataStore.data.map { it[KEY_EMAIL].orEmpty() }

    suspend fun setEmail(context: Context, email: String) {
        context.dataStore.edit { it[KEY_EMAIL] = email }
    }

    suspend fun clear(context: Context) {
        context.dataStore.edit { it.clear() }
    }
}
