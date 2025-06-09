package com.example.tarcars.data.model

import com.google.firebase.Timestamp

data class MensajeDto(
    val id: String = "",
    val autor: String = "",
    val correoAutor: String = "",
    val contenido: String = "",
    val timestamp: Timestamp = Timestamp.now()
)

