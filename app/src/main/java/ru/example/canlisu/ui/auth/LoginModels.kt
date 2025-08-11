// LoginModels.kt
package ru.example.canlisu.data.auth

data class LoginRequest(val email: String, val password: String)

data class UserDto(
    val id: Long,
    val first_name: String,
    val last_name: String,
    val email: String,
    val client_type: String
)

data class LoginResponse(val user: UserDto?)
