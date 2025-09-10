package ru.example.canlisu.data

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class Subscription(
    val id: Int,
    val name: String,
    val price: Double,
    @SerialName("duration_months")
    val durationMonths: Int
)
