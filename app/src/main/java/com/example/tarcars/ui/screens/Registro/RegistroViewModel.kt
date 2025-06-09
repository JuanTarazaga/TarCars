package com.example.tarcars.ui.screens.Registro

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarcars.data.model.UserState
import com.example.tarcars.data.repositories.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistroViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = mutableStateOf(UserState())
    val state: State<UserState> = _state

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    fun onNameChange(value: String) {
        _state.value = _state.value.copy(name = value)
        _error.value = null
    }

    fun onEmailChange(value: String) {
        _state.value = _state.value.copy(email = value)
        _error.value = null
    }

    fun onPhoneChange(value: String) {
        _state.value = _state.value.copy(phone = value)
        _error.value = null
    }

    fun onPasswordChange(value: String) {
        _state.value = _state.value.copy(password = value)
        _error.value = null
    }

    fun register(onSuccess: () -> Unit) {
        val user = state.value

        viewModelScope.launch {
            val result = authRepository.registerUser(
                user.name,
                user.email.trim(),
                user.phone,
                user.password
            )

            if (result.isSuccess) {
                    onSuccess()
            }
        }
    }
}
