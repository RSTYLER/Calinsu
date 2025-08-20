package ru.example.canlisu.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.favre.lib.crypto.bcrypt.BCrypt

class RegistrationViewModel : ViewModel() {
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val phone = MutableLiveData<String>()
    val address = MutableLiveData<String>()
    val cardNumber = MutableLiveData<String>()

    fun hashPassword(password: String): String =
        BCrypt.withDefaults().hashToString(12, password.toCharArray())
}

