package com.example.tarcars.data.model

data class Filtros(
    val marca: String? = null,
    val modelo: String? = null,
    val minPrecio: Int? = null,
    val maxPrecio: Int? = null,
    val minKm: Int? = null,
    val maxKm: Int? = null,
    val etiqueta: String? = null,
    val cambio: String? = null,
    val minCuota: Int? = null,
    val maxCuota: Int? = null,
    val minCv: Int? = null,
    val maxCv: Int? = null
)
