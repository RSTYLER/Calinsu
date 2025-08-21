package ru.example.canlisu.data

import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SupabaseClientProviderTest {

    @Test
    fun client_canConnectToDatabase() = runBlocking {
        val client = SupabaseClientProvider.client
        assertNotNull(client)

        val result = runCatching {
            client!!.from("todos").select()
        }
        assertTrue(result.isSuccess)
    }
}
