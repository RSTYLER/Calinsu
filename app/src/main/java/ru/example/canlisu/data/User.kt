package ru.example.canlisu.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int? = null,
    val email: String? = null,
    val phone: String? = null,
    @SerialName("password_hash")
    val passwordHash: String
)
