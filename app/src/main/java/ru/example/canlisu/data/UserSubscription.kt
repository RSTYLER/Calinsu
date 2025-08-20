package ru.example.canlisu.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserSubscription(
    val id: Int,
    @SerialName("user_id")
    val userId: Int,
    @SerialName("subscription_id")
    val subscriptionId: Int,
    @SerialName("start_date")
    val startDate: String,
    @SerialName("end_date")
    val endDate: String? = null,
    val active: Boolean = false
)
