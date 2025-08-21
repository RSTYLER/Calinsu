package ru.example.canlisu.data

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TodoRepositoryTest {

    @Before
    fun setup() {
        val delegateField = SupabaseClientProvider::class.java.getDeclaredField("client\$delegate")
        delegateField.isAccessible = true
        delegateField.set(null, lazy { null })

    }

    @Test
    fun loadTodos_returnsFailure_whenClientNotConfigured() = runBlocking {
        val repo = TodoRepository()
        val result = repo.loadTodos()
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalStateException)
    }
}
