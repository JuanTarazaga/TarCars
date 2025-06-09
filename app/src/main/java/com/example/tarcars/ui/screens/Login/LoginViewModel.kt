package com.example.tarcars.ui.screens.Login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarcars.data.model.UserState
import com.example.tarcars.data.repositories.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = mutableStateOf(UserState())
    val state: State<UserState> = _state

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    private val _userRole = mutableStateOf("")
    val userRole: State<String> = _userRole

    fun onEmailChange(value: String) {
        _state.value = _state.value.copy(email = value)
        _error.value = null
    }

    fun onPasswordChange(value: String) {
        _state.value = _state.value.copy(password = value)
        _error.value = null
    }

    fun login(onSuccess: (String) -> Unit) {
        viewModelScope.launch {
            val result = authRepository.loginUser(state.value.email.trim(), state.value.password)
            if (result.isSuccess) {
                val rolResult = authRepository.getUserRole()
                if (rolResult.isSuccess) {
                    onSuccess(rolResult.getOrThrow())
                } else {
                    _error.value = "Error al obtener el rol del usuario"
                }
            } else {
                _error.value = "Credenciales incorrectas"
            }
        }
    }
}

