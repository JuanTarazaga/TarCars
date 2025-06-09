package com.example.tarcars.data.model

data class Coches (
    val id: String,
    val titulo: String,
    val imagen_principal: String,
    val imagenes_adicionales: List<String>,
    val brevedescripcion: String,
    val descripcion: String,
    val precio: Int,
    val cuotamensual: Int,
    val marca: String,
    val modelo: String,
    val año: Int,
    val kilometraje: Int,
    val potencia: Int,
    val cambio: String,
    val localizacion: String,
    val etiqueta: String,
    val etiquetaMedioAmbiental: String
)

//val tesCoches = listOf(
//    Coches(
//        id = 1,
//        titulo = "Audi A4 40TDI Quattro",
//        imagen_principal = "https://example.com/audi_a4.jpg",
//        imagenes_adicionales = listOf(
//            "https://example.com/audi_a4_1.jpg",
//            "https://example.com/audi_a4_2.jpg"
//        ),
//        brevedescripcion = "Garantía 6 meses Kilómetros reales",
//        descripcion = "1 Propietario, Full equipamiento, Sin accidentes",
//        precio = 29999,
//        cuotamensual = 469,
//        marca = "Audi",
//        modelo = "A4 40TDI Quattro",
//        año = 2021,
//        kilometraje = 50000,
//        potencia = 190,
//        cambio = "Automático",
//        localizacion = "MADRID",
//        etiqueta = "Ocasión",
//        etiquetaMedioAmbiental = "Eco"
//    ),
//    Coches(
//        id = 2,
//        titulo = "BMW 320d M Sport",
//        imagen_principal = "https://example.com/bmw_320d.jpg",
//        imagenes_adicionales = listOf(
//            "https://example.com/bmw_320d_1.jpg",
//            "https://example.com/bmw_320d_2.jpg"
//        ),
//        brevedescripcion = "Garantía 12 meses",
//        descripcion = "2 Propietarios, Pack M Sport, Mantenimiento al día",
//        precio = 31500,
//        cuotamensual = 499,
//        marca = "BMW",
//        modelo = "320d M Sport",
//        año = 2020,
//        kilometraje = 35000,
//        potencia = 190,
//        cambio = "Automático",
//        localizacion = "BARCELONA",
//        etiqueta = "Seminuevo",
//        etiquetaMedioAmbiental = "C"
//    )
//)