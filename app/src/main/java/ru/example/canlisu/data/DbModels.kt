package ru.example.canlisu.data

import kotlinx.serialization.Serializable

@Serializable
data class DbSubscription(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val duration_days: Int,
    val discount: Int? = 0,
    val physicalUser: Boolean
)

@Serializable
data class DbUserSubscription(
    val id: Int,
    val user_id: String,
    val subscription_id: Int,
    val start_date: String? = null,
    val is_active: Boolean
)

@Serializable
data class DbUserSubscriptionWithSub(
    val id: Int,
    val user_id: String,
    val subscription_id: Int,
    val start_date: String? = null,
    val is_active: Boolean,
    val subscription: DbSubscription? = null
)

