package ru.example.canlisu.data

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import ru.example.canlisu.BuildConfig

object SupabaseClientProvider {

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

    fun clearForTests() {
        val field = SupabaseClientProvider::class.java.getDeclaredField("client\$delegate")
        field.isAccessible = true
        field.set(null, lazy<SupabaseClient?> { null })
    }
}
