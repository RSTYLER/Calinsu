package ru.example.canlisu.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.example.canlisu.data.AuthRepository

class PasswordRecoveryViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PasswordRecoveryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PasswordRecoveryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
