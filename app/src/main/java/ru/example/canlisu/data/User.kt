package ru.example.canlisu.data

import android.annotation.SuppressLint
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@SuppressLint("UnsafeOptInUsageError")
@Serializable
data class User(
    val id: Int? = null,
    @SerialName("firstName")
    val firstName: String? = null,
    @SerialName("lastName")
    val lastName: String? = null,
    val email: String? = null,
    val phone: String? = null,
    @SerialName("password_hash")
    val passwordHash: String
)
