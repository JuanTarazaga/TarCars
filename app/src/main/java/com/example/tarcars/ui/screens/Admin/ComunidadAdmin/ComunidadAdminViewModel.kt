package com.example.tarcars.ui.screens.Admin.ComunidadAdmin

import androidx.lifecycle.ViewModel
import com.example.tarcars.data.model.MensajeDto
import com.example.tarcars.data.repositories.ComunidadRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ComunidadAdminViewModel @Inject constructor(
    private val repository: ComunidadRepository
) : ViewModel() {

    private val _mensajes = MutableStateFlow<List<MensajeDto>>(emptyList())
    val mensajes: StateFlow<List<MensajeDto>> = _mensajes

    init {
        repository.obtenerMensajesRealtime { nuevosMensajes ->
            _mensajes.value = nuevosMensajes
        }
    }

    fun eliminarMensaje(id: String) {
        FirebaseFirestore.getInstance()
            .collection("comunidad_mensajes")
            .document(id)
            .delete()
            .addOnFailureListener {
                it.printStackTrace()
            }
    }
}