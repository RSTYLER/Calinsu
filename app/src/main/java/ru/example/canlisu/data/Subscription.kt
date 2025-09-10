package ru.example.canlisu.data

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Subscription(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    @SerialName("duration_days")
    val durationDays: Int,
    @SerialName("physicalUser")
    val physicalUser: Boolean,
    val discount: Int? = null
)
