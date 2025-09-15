package ru.example.canlisu.data

import android.util.Log
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class SupabaseRepository(
    private val client: SupabaseClient? = SupabaseClientProvider.client
) {

    suspend fun getSubscriptions(physicalUser: Boolean): List<DbSubscription> =
        client?.postgrest?.let { postgrest ->
            Log.d("SupabaseRepository", "Loading subscriptions for physicalUser=$physicalUser")

            postgrest["subscriptions"]
                .select(
                    columns = "id,name,description,price,duration_days,discount,physicalUser,user_subscriptions!inner(is_active)"
                ) {
                    filter {
                        eq("physicalUser", physicalUser)
                        eq("user_subscriptions.is_active", true)
                    }
                    order(column = "duration_days", ascending = true)
                }
                .decodeList<DbSubscription>()
        } ?: emptyList()

    suspend fun getActiveUserSubscription(userId: String): DbUserSubscriptionWithSub? =
        client?.postgrest?.let { postgrest ->
            postgrest["user_subscriptions"]
                .select(
                    columns = "id,user_id,subscription_id,start_date,is_active,subscription:subscription_id(id,name,description,price,duration_days,discount,physicalUser)"
                ) {
                    filter {
                        eq("user_id", userId)
                        eq("is_active", true)
                    }
                    limit(1)
                }
                .decodeList<DbUserSubscriptionWithSub>()
                .firstOrNull()
        }

    suspend fun getSubscriptionById(id: Int): DbSubscription? =
        client?.postgrest?.let { postgrest ->
            postgrest["subscriptions"]
                .select {
                    filter { eq("id", id) }
                    limit(1)
                }
                .decodeList<DbSubscription>()
                .firstOrNull()
        }
}

