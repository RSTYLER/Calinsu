package ru.example.canlisu.data

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class AuthRepositoryTest {

    private val sampleUser = User(email = "test@example.com", phone = null, passwordHash = "hash")

    @Test
    fun register_returnsFailure_whenClientNotConfigured() = runBlocking {
        val repo = AuthRepository(client = null)
        val result = repo.register(sampleUser, "hash")
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalStateException)
    }

    @Test
    fun login_returnsFailure_whenClientNotConfigured() = runBlocking {
        val repo = AuthRepository(client = null)
        val result = repo.login("test@example.com", "pass")
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalStateException)
    }
}
