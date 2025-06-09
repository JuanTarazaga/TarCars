package com.example.tarcars.data.repositories

import com.example.tarcars.data.model.MensajeDto
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import jakarta.inject.Inject

class ComunidadRepository @Inject constructor() {

    private val db = FirebaseFirestore.getInstance()
    private val mensajesRef = db.collection("comunidad_mensajes")

    fun obtenerMensajesRealtime(onChange: (List<MensajeDto>) -> Unit) {
        mensajesRef
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null || snapshot == null) return@addSnapshotListener

                val mensajes = snapshot.documents.mapNotNull { it.toObject(MensajeDto::class.java) }
                onChange(mensajes)
            }
    }
}
