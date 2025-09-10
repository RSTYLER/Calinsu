package ru.example.canlisu.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class SubscriptionRepository(
    private val client: SupabaseClient? = SupabaseClientProvider.client,
) {
    suspend fun getSubscriptions(isPhysicalUser: Boolean): Result<List<Subscription>> {
        val supabase = client ?: return Result.failure(IllegalStateException("Supabase client is not configured"))
        return runCatching {
            supabase.postgrest["subscriptions"].select {
                filter {
                    eq("physicalUser", isPhysicalUser)
                }
            }.decodeList<Subscription>()
        }
    }

    suspend fun getSubscriptionById(id: Int): Result<Subscription> {
        val supabase = client ?: return Result.failure(IllegalStateException("Supabase client is not configured"))
        return runCatching {
            supabase.postgrest["subscriptions"].select {
                filter {
                    eq("id", id)
                }
            }.decodeSingle<Subscription>()
        }
    }

    suspend fun getActiveSubscription(userId: String): Result<UserSubscription?> {
        val supabase = client ?: return Result.failure(IllegalStateException("Supabase client is not configured"))
        return runCatching {
            supabase.postgrest["user_subscriptions"].select {
                filter {
                    eq("user_id", userId)
                    eq("is_active", true)
                }
            }.decodeList<UserSubscription>().firstOrNull()
        }
    }
}
