package com.example.cartrack.login

import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cartrack.entity.AppUser
import com.example.cartrack.repository.UserRepository
import com.example.cartrack.util.*
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository, private val sharedPref: SharedPreferences
) : ViewModel() {
    private var _result = MutableLiveData<Result<AppUser>>()
    private var _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error
    val result: LiveData<Result<AppUser>> get() = _result

    fun login(view: View) {
        val email = emailObservable.text
        val password = passwordObservable.text

        when {
            email.isNullOrBlank() -> {
                _error.value = ErrorObject.EMAIL_OBJECT
            }
            password.isNullOrBlank() -> {
                _error.value = ErrorObject.PASSWORD_OBJECT
            }
            else -> {
                val passwordHash: String = hashAndSavePasswordHash(password)
                viewModelScope.launch {
                    val result2 = userRepository.login(email, passwordHash)
                    _result.value = result2.value
                }
            }
        }

    }

    fun register(view: View) {
        _error.value = AppConstant.NAVIGATE_TO_REGISTER
    }

    fun loggedInOrNot(): Boolean {
        return sharedPref.loadLoginSharedPrefState()
    }

    val emailObservable = TextObservable()
    val passwordObservable = TextObservable()

    fun hashAndSavePasswordHash(clearPassword: String): String {
        val digest = MessageDigest.getInstance("SHA-1")
        val result = digest.digest(clearPassword.toByteArray(Charsets.UTF_8))
        val sb = StringBuilder()
        for (b in result) {
            sb.append(String.format("%02X", b))
        }
        return sb.toString()
    }
}