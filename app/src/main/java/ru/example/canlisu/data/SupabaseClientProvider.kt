package ru.example.canlisu.data

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import ru.example.canlisu.BuildConfig

object SupabaseClientProvider {

    @Volatile
    private var _client: SupabaseClient? = null
    @Volatile
    private var allowInit: Boolean = true

    val client: SupabaseClient?
        get() {
            initClientIfNeeded()
            return _client
        }

    private fun initClientIfNeeded() {
        if (!allowInit || _client != null) return
        val url = BuildConfig.SUPABASE_URL
        val key = BuildConfig.SUPABASE_KEY
        if (url.isBlank() || key.isBlank()) {
            Log.e("SupabaseClientProvider", "Supabase credentials are missing")
            return
        }
        _client = createSupabaseClient(
            supabaseUrl = url,
            supabaseKey = key
        ) {
            install(Postgrest)
            install(Auth)
        }
    }

    fun clearForTests() {
        _client = null
        allowInit = false
    }
}
