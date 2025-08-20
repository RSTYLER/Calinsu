package ru.example.canlisu.data

import at.favre.lib.crypto.bcrypt.BCrypt
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class AuthRepository(
    private val client: SupabaseClient? = SupabaseClientProvider.client,
) {

    suspend fun register(user: User, passwordHash: String): Result<Unit> {
        val supabase = client ?: return Result.failure(IllegalStateException("Supabase client is not configured"))
        val data = mapOf(
            "email" to user.email,
            "phone" to user.phone,
            "password_hash" to passwordHash,
        )
        return runCatching {
            supabase.postgrest["users"].insert(data)
            Unit
        }
    }

    suspend fun login(emailOrPhone: String, password: String): Result<User?> {
        val supabase = client ?: return Result.failure(IllegalStateException("Supabase client is not configured"))
        return runCatching {
            val users = supabase.postgrest["users"].select {
                filter {
                    or {
                        eq("email", emailOrPhone)
                        eq("phone", emailOrPhone)
                    }
                }
                limit(1)
            }.decodeList<User>()

            val user = users.firstOrNull() ?: return@runCatching null
            val verified = BCrypt.verifyer().verify(password.toCharArray(), user.passwordHash)
            if (verified.verified) user else null
        }
    }
}
