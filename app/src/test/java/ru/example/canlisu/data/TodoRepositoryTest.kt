package ru.example.canlisu.data

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TodoRepositoryTest {

    @Before
    fun setup() {
        SupabaseClientProvider.clearForTests()
    }

    @Test
    fun loadTodos_returnsFailure_whenClientNotConfigured() = runBlocking {
        val repo = TodoRepository()
        val result = repo.loadTodos()
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalStateException)
    }
}
