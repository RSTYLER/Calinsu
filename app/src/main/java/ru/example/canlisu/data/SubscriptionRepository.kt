package ru.example.canlisu.data

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.decodeList
import io.github.jan.supabase.postgrest.eq
import io.github.jan.supabase.postgrest.filter
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.select

class SubscriptionRepository(
    private val client: SupabaseClient = SupabaseClientProvider.client
) {
    suspend fun getAvailableSubscriptions(): List<Subscription> {
        return client.postgrest["subscriptions"].select().decodeList<Subscription>()
    }

    suspend fun getActiveSubscriptions(userId: Int): List<UserSubscription> {
        return client.postgrest["user_subscriptions"].select {
            filter {
                eq("user_id", userId)
                eq("active", true)
            }
        }.decodeList<UserSubscription>()
    }
}
