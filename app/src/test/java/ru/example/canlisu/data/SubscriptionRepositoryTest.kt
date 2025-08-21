package ru.example.canlisu.data

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class SubscriptionRepositoryTest {

    @Test
    fun getAvailableSubscriptions_returnsFailure_whenClientNotConfigured() = runBlocking {
        val repo = SubscriptionRepository(client = null)
        val result = repo.getAvailableSubscriptions()
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalStateException)
    }

    @Test
    fun getActiveSubscriptions_returnsFailure_whenClientNotConfigured() = runBlocking {
        val repo = SubscriptionRepository(client = null)
        val result = repo.getActiveSubscriptions(1)
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalStateException)
    }
}
