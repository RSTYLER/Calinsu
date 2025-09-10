package ru.example.canlisu.prefs

import android.content.Context
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.example.canlisu.data.User

object AuthPrefs {
    private const val PREFS_NAME = "auth"
    private const val KEY_REMEMBER = "remember_me"
    private const val KEY_USER = "user"

    fun saveUser(context: Context, user: User) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putBoolean(KEY_REMEMBER, true)
            .putString(KEY_USER, Json.encodeToString(user))
            .apply()
    }

    fun clear(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }

    fun getUser(context: Context): User? {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return if (prefs.getBoolean(KEY_REMEMBER, false)) {
            prefs.getString(KEY_USER, null)?.let { Json.decodeFromString<User>(it) }
        } else {
            null
        }
    }
}

