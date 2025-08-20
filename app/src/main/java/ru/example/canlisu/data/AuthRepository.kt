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
    private val client: SupabaseClient = SupabaseClientProvider.client
) {

    suspend fun register(user: User, passwordHash: String) {
        val data = mapOf(
            "email" to user.email,
            "phone" to user.phone,
            "password_hash" to passwordHash
        )
        client.postgrest["users"].insert(data)
    }

    suspend fun login(emailOrPhone: String, password: String): User? {
        val users = client.postgrest["users"].select {
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
