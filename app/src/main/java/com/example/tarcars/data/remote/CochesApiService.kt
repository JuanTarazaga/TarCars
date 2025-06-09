package com.example.tarcars.data.remote

import com.example.tarcars.data.model.Coches
import retrofit2.Response
import retrofit2.http.*

interface CochesApiService {

    @GET("Coches/")
    suspend fun obtenerCoches(): Response<List<Coches>>

    @GET("Coches/{id}")
    suspend fun obtenerCochePorId(@Path("id") id: String): Response<Coches>

    @POST("Coches")
    suspend fun crearCoche(@Body coche: Coches): Response<Coches>

    @PUT("Coches/{id}")
    suspend fun actualizarCoche(
        @Path("id") id: String,
        @Body coche: Coches
    ): Response<Coches>

    @DELETE("Coches/{id}")
    suspend fun eliminarCoche(@Path("id") id: String): Response<Coches>

    @GET("Coches")
    suspend fun obtenerCochesFiltrados(
        @Query("marca") marca: String? = null,
        @Query("modelo") modelo: String? = null,
        @Query("minPrecio") minPrecio: Int? = null,
        @Query("maxPrecio") maxPrecio: Int? = null,
        @Query("minKm") minKm: Int? = null,
        @Query("maxKm") maxKm: Int? = null,
        @Query("etiqueta") etiqueta: String? = null,
        @Query("cambio") cambio: String? = null,
        @Query("minCuota") minCuota: Int? = null,
        @Query("maxCuota") maxCuota: Int? = null,
        @Query("minCv") minCv: Int? = null,
        @Query("maxCv") maxCv: Int? = null
    ): Response<List<Coches>>

    @GET("Coches/marcas")
    suspend fun obtenerMarcas(): Response<List<String>>

    @GET("Coches/modelos")
    suspend fun obtenerModelos(): Response<List<String>>

    @GET("Coches/etiquetas")
    suspend fun obtenerEtiquetas(): Response<List<String>>

    @GET("Coches/cambios")
    suspend fun obtenerCambios(): Response<List<String>>
}
