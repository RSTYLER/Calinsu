package ru.example.canlisu.data

import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.result.decodeList

class TodoRepository {

    suspend fun loadTodos(): Result<List<TodoItem>> {
        val client = SupabaseClientProvider.client
            ?: return Result.failure(IllegalStateException("Supabase client is not configured"))

        return try {
            val items = client.from("todos").select().decodeList<TodoItem>()
            Result.success(items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
