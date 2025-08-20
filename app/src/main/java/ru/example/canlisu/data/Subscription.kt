package ru.example.canlisu.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Subscription(
    val id: Int,
    val name: String,
    val price: Double,
    @SerialName("duration_months")
    val durationMonths: Int
)
