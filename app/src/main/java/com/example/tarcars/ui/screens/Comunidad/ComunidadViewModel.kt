package com.example.tarcars.ui.screens.Comunidad

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
class ComunidadViewModel @Inject constructor(
    private val repository: ComunidadRepository
) : ViewModel() {

    private val _baneado = MutableStateFlow(false)
    val baneado: StateFlow<Boolean> = _baneado

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    private val _enviando = MutableStateFlow(false)
    val enviando: StateFlow<Boolean> = _enviando

    private val _mensajes = MutableStateFlow<List<MensajeDto>>(emptyList())
    val mensajes: StateFlow<List<MensajeDto>> = _mensajes

    init {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            FirebaseFirestore.getInstance().collection("usuarios").document(user.uid)
                .get()
                .addOnSuccessListener { doc ->
                    _baneado.value = doc.getBoolean("baneado") == true
                }
        }

        repository.obtenerMensajesRealtime { nuevosMensajes ->
            _mensajes.value = nuevosMensajes
        }
    }

    fun onMensajeChange(nuevoMensaje: String) {
        _mensaje.value = nuevoMensaje
    }

    fun enviarMensaje() {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        val uid = user.uid
        val correo = user.email ?: "desconocido@email.com"

        _enviando.value = true

        FirebaseFirestore.getInstance().collection("usuarios").document(uid)
            .get()
            .addOnSuccessListener { doc ->
                val nombre = doc.getString("nombre") ?: "Desconocido"

                val nuevoMensaje = MensajeDto(
                    id = UUID.randomUUID().toString(),
                    autor = nombre,
                    correoAutor = correo,
                    contenido = _mensaje.value.trim(),
                    timestamp = Timestamp.now()
                )

                FirebaseFirestore.getInstance()
                    .collection("comunidad_mensajes")
                    .document(nuevoMensaje.id)
                    .set(nuevoMensaje)
                    .addOnSuccessListener {
                        _mensaje.value = ""
                    }
                    .addOnFailureListener { e ->
                        e.printStackTrace()
                    }
                    .addOnCompleteListener {
                        _enviando.value = false
                    }
            }
            .addOnFailureListener {
                _enviando.value = false
                it.printStackTrace()
            }
    }
}
