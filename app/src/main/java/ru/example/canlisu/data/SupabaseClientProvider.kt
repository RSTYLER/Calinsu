package ru.example.canlisu.data

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import ru.example.canlisu.BuildConfig

object SupabaseClientProvider {

    /**
     * Lazily creates the [SupabaseClient]. If the URL or key are missing
     * in the build config the client will not be created and `null` will be
     * returned instead. This prevents the application from crashing on start
     * when the developer has not provided the required credentials.
     */
    val client: SupabaseClient? by lazy {
        val url = BuildConfig.SUPABASE_URL
        val key = BuildConfig.SUPABASE_KEY
        if (url.isBlank() || key.isBlank()) {
            Log.e("SupabaseClientProvider", "Supabase credentials are missing")
            null
        } else {
            createSupabaseClient(
                supabaseUrl = url,
                supabaseKey = key
            ) {
                install(Postgrest)
                install(Auth)
            }
        }
    }
}
