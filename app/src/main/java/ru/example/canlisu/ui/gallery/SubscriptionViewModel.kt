package ru.example.canlisu.ui.gallery

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.example.canlisu.data.DbSubscription
import ru.example.canlisu.data.DbUserSubscriptionWithSub
import ru.example.canlisu.data.SupabaseClientProvider
import ru.example.canlisu.data.SupabaseRepository

sealed interface SubscriptionsUiState {
    object Loading : SubscriptionsUiState
    data class Data(val items: List<DbSubscription>) : SubscriptionsUiState
    object Empty : SubscriptionsUiState
    data class Error(val throwable: Throwable) : SubscriptionsUiState
}

class SubscriptionViewModel(
    private val repository: SupabaseRepository = SupabaseRepository()
) : ViewModel() {

    private val _subscriptions = MutableStateFlow<SubscriptionsUiState>(SubscriptionsUiState.Empty)
    val subscriptions: StateFlow<SubscriptionsUiState> = _subscriptions

    private val _activeSubscription = MutableStateFlow<DbUserSubscriptionWithSub?>(null)
    val activeSubscription: StateFlow<DbUserSubscriptionWithSub?> = _activeSubscription

    private val client = SupabaseClientProvider.client

    fun loadActiveSubscription() {
        viewModelScope.launch {
            val uid = client?.gotrue?.currentUserOrNull()?.id
            Log.d("SubscriptionViewModel", "Current uid=$uid")
            if (uid == null) {
                _activeSubscription.value = null
                return@launch
            }
            try {
                val subscription = repository.getActiveUserSubscription(userId = uid)
                _activeSubscription.value = subscription
                Log.d(
                    "SubscriptionViewModel",
                    "Active subscription present=${subscription != null}"
                )
            } catch (e: Exception) {
                _activeSubscription.value = null
                Log.e(
                    "SubscriptionViewModel",
                    "Failed to load active subscription",
                    e
                )
            }
        }
    }

    fun loadSubscriptions(physicalUser: Boolean) {
        viewModelScope.launch {
            _subscriptions.value = SubscriptionsUiState.Loading
            runCatching { repository.getSubscriptions(physicalUser) }
                .onSuccess { list ->
                    Log.d(
                        "SubscriptionViewModel",
                        "Loaded ${list.size} items for physicalUser=$physicalUser"
                    )
                    _subscriptions.value = if (list.isEmpty()) {
                        SubscriptionsUiState.Empty
                    } else {
                        SubscriptionsUiState.Data(list)
                    }
                }
                .onFailure { e ->
                    Log.e("SubscriptionViewModel", "Error loading subscriptions", e)
                    _subscriptions.value = SubscriptionsUiState.Error(e)
                }
        }
    }
}

