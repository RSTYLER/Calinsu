package ru.example.canlisu.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.example.canlisu.data.User
import ru.example.canlisu.data.UserManager

class HomeViewModel : ViewModel() {

    private val _user = MutableLiveData<User?>().apply {
        value = UserManager.currentUser
    }
    val user: LiveData<User?> = _user
}

