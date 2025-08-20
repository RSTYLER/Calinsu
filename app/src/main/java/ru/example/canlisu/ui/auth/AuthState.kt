package ru.example.canlisu.ui.auth

sealed class AuthState<out T> {
    object Loading : AuthState<Nothing>()
    data class Success<T>(val data: T) : AuthState<T>()
    data class Error(val message: String) : AuthState<Nothing>()
}
