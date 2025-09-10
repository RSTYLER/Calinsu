package ru.example.canlisu.data

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class UserSubscription(
    val id: Int,
    @SerialName("user_id")
    val userId: String,
    @SerialName("subscription_id")
    val subscriptionId: Int,
    @SerialName("start_date")
    val startDate: String,
    @SerialName("is_active")
    val isActive: Boolean = false
)
