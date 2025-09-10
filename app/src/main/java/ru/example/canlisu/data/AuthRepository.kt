package ru.example.canlisu.data

import at.favre.lib.crypto.bcrypt.BCrypt
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.postgrest

import ru.example.canlisu.data.EmailAlreadyExistsException
import ru.example.canlisu.data.PhoneAlreadyExistsException

class AuthRepository(
    private val client: SupabaseClient? = SupabaseClientProvider.client,
) {

    suspend fun register(user: User, passwordHash: String): Result<Unit> {
        val supabase = client ?: return Result.failure(IllegalStateException("Supabase client is not configured"))
        val data = mapOf(
            "firstName" to user.firstName,
            "lastName" to user.lastName,
            "email" to user.email,
            "phone" to user.phone,
            "password_hash" to passwordHash,
        )
        return runCatching {
            supabase.postgrest["users"].insert(data)
            Unit
        }.recoverCatching { e ->
            val message = e.message.orEmpty()
            when {
                message.contains("users_email_key") -> throw EmailAlreadyExistsException()
                message.contains("users_phone_key") -> throw PhoneAlreadyExistsException()
                else -> throw e
            }
        }
    }

    suspend fun login(emailOrPhone: String, password: String): Result<User> {
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

            val user = users.firstOrNull() ?: throw UserNotFoundException()
            val verified = BCrypt.verifyer().verify(password.toCharArray(), user.passwordHash)
            if (!verified.verified) throw InvalidPasswordException()
            user
        }
    }

    suspend fun resetPassword(email: String): Result<Unit> {
        val supabase = client ?: return Result.failure(IllegalStateException("Supabase client is not configured"))
        return runCatching {
            supabase.auth.resetPasswordForEmail(email)
        }
    }
}
