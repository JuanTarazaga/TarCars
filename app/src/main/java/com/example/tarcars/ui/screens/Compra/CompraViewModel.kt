package com.example.tarcars.ui.screens.Compra

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarcars.BuildConfig
import com.example.tarcars.data.model.EmailRequest
import com.example.tarcars.data.remote.RetrofitInstance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CompraViewModel @Inject constructor() : ViewModel() {
    var nombre by mutableStateOf("")
    var modeloCoche by mutableStateOf("")
    var descripcion by mutableStateOf("")
    var telefono by mutableStateOf("")
    var enviando by mutableStateOf(false)
        private set

    fun formularioEsValido(): Boolean {
        return nombre.isNotBlank() &&
                modeloCoche.isNotBlank() &&
                telefono.length >= 9
    }

    fun comenzarEnvio() {
        enviando = true
    }

    fun finalizarEnvio() {
        enviando = false
    }

    fun enviarConEmailJS(
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            val request = EmailRequest(
                service_id = BuildConfig.EMAILJS_SERVICE_ID,
                template_id = BuildConfig.EMAILJS_TEMPLATE_ID,
                user_id = BuildConfig.EMAILJS_USER_ID,
                template_params = mapOf(
                    "nombre" to nombre,
                    "modelo" to modeloCoche,
                    "descripcion" to descripcion,
                    "telefono" to telefono
                )
            )

            try {
                RetrofitInstance.api.sendEmail(request)
                Log.d("EmailJS", "Correo enviado correctamente")
                onSuccess()
            } catch (e: HttpException) {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("EmailJS", "HTTP error: ${e.code()} - $errorBody", e)
                onFailure("Error ${e.code()}: $errorBody")
            }
        }
    }
}
