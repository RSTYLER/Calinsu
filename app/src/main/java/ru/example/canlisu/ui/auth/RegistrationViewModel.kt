package ru.example.canlisu.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.favre.lib.crypto.bcrypt.BCrypt
import kotlinx.coroutines.launch
import ru.example.canlisu.data.AuthRepository
import ru.example.canlisu.data.EmailAlreadyExistsException
import ru.example.canlisu.data.PhoneAlreadyExistsException
import ru.example.canlisu.data.AddressRequiredException
import ru.example.canlisu.data.User

class RegistrationViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _registrationState = MutableLiveData<AuthState<User>>()
    val registrationState: LiveData<AuthState<User>> = _registrationState

    fun register(firstName: String, lastName: String, email: String, phone: String, address: String, password: String) {
        viewModelScope.launch {
            _registrationState.value = AuthState.Loading
            val hashedPassword = hashPassword(password)
            val user = User(
                firstName = firstName,
                lastName = lastName,
                email = email,
                phone = phone,
                address = address,
                passwordHash = hashedPassword
            )
            repository.register(user, hashedPassword)
                .onSuccess { created ->
                    _registrationState.value = AuthState.Success(created)
                }
                .onFailure { e ->
                    _registrationState.value = when (e) {
                        is EmailAlreadyExistsException -> AuthState.Error("email_exists")
                        is PhoneAlreadyExistsException -> AuthState.Error("phone_exists")
                        is AddressRequiredException -> AuthState.Error("address_required")
                        is IllegalStateException -> AuthState.Error("Supabase client is not configured. Please configure Supabase client.")
                        else -> AuthState.Error(e.message ?: "Network error")
                    }
                }
        }
    }

    private fun hashPassword(password: String): String =
        BCrypt.withDefaults().hashToString(12, password.toCharArray())
}
