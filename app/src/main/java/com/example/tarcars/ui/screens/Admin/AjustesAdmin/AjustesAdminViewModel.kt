package com.example.tarcars.ui.screens.Admin.AjustesAdmin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.tarcars.ui.components.navigation.Destinations
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AjustesAdminViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val user = auth.currentUser

    private val _nombre = MutableStateFlow("")
    val nombre = _nombre.asStateFlow()

    private val _correo = MutableStateFlow("")
    val correo = _correo.asStateFlow()

    private val _telefono = MutableStateFlow("")
    val telefono = _telefono.asStateFlow()

    private val _mensaje = MutableStateFlow<String?>(null)
    val mensaje = _mensaje.asStateFlow()

    init {
        cargarDatosUsuario()
    }

    private fun cargarDatosUsuario() {
        user?.let { u ->
            _correo.value = u.email ?: ""

            firestore.collection("usuarios").document(u.uid).get()
                .addOnSuccessListener { doc ->
                    _nombre.value = doc.getString("nombre") ?: ""
                    _telefono.value = doc.getString("telefono") ?: ""
                }
                .addOnFailureListener {
                    _mensaje.value = "Error al cargar los datos."
                }
        }
    }

    fun actualizarDatos(
        nuevoNombre: String,
        nuevoTelefono: String,
        contrasenaActual: String,
        nuevaContrasena: String,
        onResult: (String) -> Unit
    ) {
        val uid = user?.uid ?: return

        val datos = mapOf("nombre" to nuevoNombre, "telefono" to nuevoTelefono)
        firestore.collection("usuarios").document(uid).update(datos)
            .addOnSuccessListener {
                if (contrasenaActual.isNotBlank() && nuevaContrasena.isNotBlank()) {
                    cambiarContrasena(contrasenaActual, nuevaContrasena, onResult)
                } else {
                    onResult("Datos actualizados correctamente.")
                }
            }
            .addOnFailureListener {
                onResult("Error al actualizar datos.")
            }
    }

    private fun cambiarContrasena(
        contrasenaActual: String,
        nuevaContrasena: String,
        onResult: (String) -> Unit
    ) {
        val credential = EmailAuthProvider.getCredential(correo.value, contrasenaActual)
        user?.reauthenticate(credential)?.addOnCompleteListener { authTask ->
            if (authTask.isSuccessful) {
                user.updatePassword(nuevaContrasena)
                    .addOnSuccessListener { onResult("Contraseña actualizada correctamente.") }
                    .addOnFailureListener { onResult("Error al actualizar la contraseña.") }
            } else {
                onResult("La contraseña actual es incorrecta.")
            }
        }
    }

    fun cerrarSesion(navController: NavController) {
        auth.signOut()
        navController.navigate(Destinations.LOGIN_SCREEN) {
            popUpTo(0) { inclusive = true }
        }
    }

    fun asignarAdminPorCorreo(correo: String, onResult: (String) -> Unit) {
        firestore.collection("usuarios")
            .whereEqualTo("correo", correo)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val userDoc = documents.documents.first()
                    firestore.collection("usuarios").document(userDoc.id)
                        .update("rol", "admin")
                        .addOnSuccessListener {
                            onResult("Rol de administrador asignado correctamente.")
                        }
                        .addOnFailureListener {
                            onResult("Error al asignar el rol de administrador.")
                        }
                } else {
                    onResult("No se encontró ningún usuario con ese correo.")
                }
            }
            .addOnFailureListener {
                onResult("Error al buscar el usuario.")
            }
    }

    fun banearUsuarioPorCorreo(correo: String, onResult: (String) -> Unit) {
        firestore.collection("usuarios")
            .whereEqualTo("correo", correo)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val userDoc = documents.documents.first()
                    firestore.collection("usuarios").document(userDoc.id)
                        .update("baneado", true)
                        .addOnSuccessListener {
                            onResult("Usuario baneado correctamente.")
                        }
                        .addOnFailureListener {
                            onResult("Error al banear el usuario.")
                        }
                } else {
                    onResult("No se encontró ningún usuario con ese correo.")
                }
            }
            .addOnFailureListener {
                onResult("Error al buscar el usuario.")
            }
    }

    fun desbanearUsuarioPorCorreo(correo: String, onResult: (String) -> Unit) {
        firestore.collection("usuarios")
            .whereEqualTo("correo", correo)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    val userDoc = documents.documents.first()
                    firestore.collection("usuarios").document(userDoc.id)
                        .update("baneado", false)
                        .addOnSuccessListener {
                            onResult("Usuario desbaneado correctamente.")
                        }
                        .addOnFailureListener {
                            onResult("Error al desbanear el usuario.")
                        }
                } else {
                    onResult("No se encontró ningún usuario con ese correo.")
                }
            }
            .addOnFailureListener {
                onResult("Error al buscar el usuario.")
            }
    }
}