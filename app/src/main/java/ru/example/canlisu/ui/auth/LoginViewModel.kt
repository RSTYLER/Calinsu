package ru.example.canlisu.ui.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import at.favre.lib.crypto.bcrypt.BCrypt

class LoginViewModel : ViewModel() {
    val email = MutableLiveData<String>()
    val phone = MutableLiveData<String>()

    fun verifyPassword(password: String, hashedPassword: String): Boolean =
        BCrypt.verifyer().verify(password.toCharArray(), hashedPassword).verified
}
