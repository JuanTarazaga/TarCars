package com.example.tarcars.ui.screens.RecuperarContraseña

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class RecuperarContraseñaViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email

    private val _emailError = MutableStateFlow("")
    val emailError: StateFlow<String> = _emailError

    fun onEmailChange(newEmail: String) {
        _email.value = newEmail
        _emailError.value = ""
    }

    fun validarEmail(): Boolean {
        return email.value.isNotBlank() && !email.value.contains(" ")
    }

    fun enviarCorreoRecuperacion(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (!validarEmail()) {
            _emailError.value = "El correo no puede contener espacios"
            onFailure("Correo inválido")
            return
        }

        auth.sendPasswordResetEmail(email.value.trim())
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener {
                _emailError.value = "Error al enviar el correo. Inténtalo de nuevo."
                onFailure(it.localizedMessage ?: "Error desconocido")
            }
    }
}
