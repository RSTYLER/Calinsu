package ru.example.canlisu.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.favre.lib.crypto.bcrypt.BCrypt
import kotlinx.coroutines.launch
import ru.example.canlisu.data.AuthRepository
import ru.example.canlisu.data.User

class RegistrationViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _registrationState = MutableLiveData<AuthState<Unit>>()
    val registrationState: LiveData<AuthState<Unit>> = _registrationState

    fun register(email: String, phone: String, password: String) {
        viewModelScope.launch {
            _registrationState.value = AuthState.Loading
            try {
                val hashedPassword = hashPassword(password)
                val user = User(email = email, phone = phone, passwordHash = hashedPassword)
                repository.register(user, hashedPassword)
                _registrationState.value = AuthState.Success(Unit)
            } catch (e: Exception) {
                _registrationState.value = AuthState.Error(e.message ?: "Network error")
            }
        }
    }

    private fun hashPassword(password: String): String =
        BCrypt.withDefaults().hashToString(12, password.toCharArray())
}
