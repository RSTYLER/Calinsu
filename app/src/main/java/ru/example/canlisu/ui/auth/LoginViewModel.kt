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
            try {
                val user = repository.login(login, password)
                if (user != null) {
                    _loginState.value = AuthState.Success(user)
                } else {
                    _loginState.value = AuthState.Error("invalid_credentials")
                }
            } catch (e: Exception) {
                _loginState.value = AuthState.Error(e.message ?: "Network error")
            }
        }
    }
}
