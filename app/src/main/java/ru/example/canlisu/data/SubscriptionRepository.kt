package ru.example.canlisu.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class SubscriptionRepository(
    private val client: SupabaseClient? = SupabaseClientProvider.client,
) {
    suspend fun getAvailableSubscriptions(): Result<List<Subscription>> {
        val supabase = client ?: return Result.failure(IllegalStateException("Supabase client is not configured"))
        return runCatching {
            supabase.postgrest["subscriptions"].select().decodeList<Subscription>()
        }
    }

    suspend fun getActiveSubscriptions(userId: Int): Result<List<UserSubscription>> {
        val supabase = client ?: return Result.failure(IllegalStateException("Supabase client is not configured"))
        return runCatching {
            supabase.postgrest["user_subscriptions"].select {
                filter {
                    eq("user_id", userId)
                    eq("active", true)
                }
            }.decodeList<UserSubscription>()
        }
    }
}
