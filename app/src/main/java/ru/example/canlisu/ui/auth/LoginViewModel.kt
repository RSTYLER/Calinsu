package ru.example.canlisu.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.example.canlisu.data.AuthRepository
import ru.example.canlisu.data.User

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _loginState = MutableLiveData<AuthState<User>>()
    val loginState: LiveData<AuthState<User>> = _loginState

    fun login(login: String, password: String) {
        viewModelScope.launch {
            _loginState.value = AuthState.Loading
            repository.login(login, password)
                .onSuccess { user ->
                    if (user != null) {
                        _loginState.value = AuthState.Success(user)
                    } else {
                        _loginState.value = AuthState.Error("invalid_credentials")
                    }
                }
                .onFailure { e ->
                    _loginState.value = if (e is IllegalStateException) {
                        AuthState.Error("Supabase client is not configured. Please configure Supabase.")
                    } else {
                        AuthState.Error(e.message ?: "Network error")
                    }
                }
        }
    }
}
