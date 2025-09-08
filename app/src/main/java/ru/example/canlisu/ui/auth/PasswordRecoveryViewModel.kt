package ru.example.canlisu.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.example.canlisu.data.AuthRepository

class PasswordRecoveryViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _resetState = MutableLiveData<AuthState<Unit>>()
    val resetState: LiveData<AuthState<Unit>> = _resetState

    fun resetPassword(email: String) {
        viewModelScope.launch {
            _resetState.value = AuthState.Loading
            repository.resetPassword(email)
                .onSuccess {
                    _resetState.value = AuthState.Success(Unit)
                }
                .onFailure { e ->
                    _resetState.value = if (e is IllegalStateException) {
                        AuthState.Error("Supabase client is not configured. Please configure Supabase.")
                    } else {
                        AuthState.Error(e.message ?: "Network error")
                    }
                }
        }
    }
}
