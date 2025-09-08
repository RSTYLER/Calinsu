package ru.example.canlisu.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.example.canlisu.data.AuthRepository
import ru.example.canlisu.data.User
import ru.example.canlisu.data.InvalidPasswordException
import ru.example.canlisu.data.UserNotFoundException

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _loginState = MutableLiveData<AuthState<User>>()
    val loginState: LiveData<AuthState<User>> = _loginState

    fun login(login: String, password: String) {
        viewModelScope.launch {
            _loginState.value = AuthState.Loading
            repository.login(login, password)
                .onSuccess { user ->
                    _loginState.value = AuthState.Success(user)
                }
                .onFailure { e ->
                    _loginState.value = when (e) {
                        is UserNotFoundException -> AuthState.Error("user_not_found")
                        is InvalidPasswordException -> AuthState.Error("invalid_password")
                        is IllegalStateException -> AuthState.Error("Supabase client is not configured. Please configure Supabase.")
                        else -> AuthState.Error(e.message ?: "Network error")
                    }
                }
        }
    }
}
