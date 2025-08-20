package ru.example.canlisu.data

import at.favre.lib.crypto.bcrypt.BCrypt
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.decodeList
import io.github.jan.supabase.postgrest.eq
import io.github.jan.supabase.postgrest.filter
import io.github.jan.supabase.postgrest.insert
import io.github.jan.supabase.postgrest.limit
import io.github.jan.supabase.postgrest.or
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.select

class AuthRepository(
    private val client: SupabaseClient? = SupabaseClientProvider.client,
) {

    suspend fun register(user: User, passwordHash: String) {
        val supabase = client ?: throw IllegalStateException("Supabase client is not configured")
        val data = mapOf(
            "email" to user.email,
            "phone" to user.phone,
            "password_hash" to passwordHash,
        )
        supabase.postgrest["users"].insert(data)
    }

    suspend fun login(emailOrPhone: String, password: String): User? {
        val supabase = client ?: throw IllegalStateException("Supabase client is not configured")
        val users = supabase.postgrest["users"].select {
            filter {
                or {
                    eq("email", emailOrPhone)
                    eq("phone", emailOrPhone)
                }
            }
            limit(1)
        }.decodeList<User>()

        val user = users.firstOrNull() ?: return null
        val verified = BCrypt.verifyer().verify(password.toCharArray(), user.passwordHash)
        return if (verified.verified) user else null
    }
}
