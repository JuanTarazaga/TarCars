package com.example.tarcars.data.repositories

import android.util.Log
import com.example.tarcars.data.model.Coches
import com.example.tarcars.data.model.Filtros
import com.example.tarcars.data.remote.RetrofitInstance

class CochesRepository {

    suspend fun getCoches(): List<Coches>? {
        return try {
            val response = RetrofitInstance.apiCoches.obtenerCoches()
            if (response.isSuccessful) {
                response.body() ?: throw Exception("Body vacío")
            } else {
                throw Exception("Error ${response.code()}: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error al obtener coches", e)
            null
        }
    }

    suspend fun getCoche(id: String): Coches? {
        return try {
            val response = RetrofitInstance.apiCoches.obtenerCochePorId(id)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error al obtener coche por ID", e)
            null
        }
    }

    suspend fun postCoche(coche: Coches): Coches? {
        return try {
            val response = RetrofitInstance.apiCoches.crearCoche(coche)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error al crear coche", e)
            null
        }
    }

    suspend fun putCoche(coche: Coches): Coches? {
        return try {
            val response = RetrofitInstance.apiCoches.actualizarCoche(coche.id, coche)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error al actualizar coche", e)
            null
        }
    }

    suspend fun deleteCoche(id: String): Coches? {
        return try {
            val response = RetrofitInstance.apiCoches.eliminarCoche(id)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error al eliminar coche", e)
            null
        }
    }

    suspend fun getCochesFiltrados(filtros: Filtros): List<Coches>? {
        return try {
            val response = RetrofitInstance.apiCoches.obtenerCochesFiltrados(
                marca = filtros.marca,
                modelo = filtros.modelo,
                minPrecio = filtros.minPrecio,
                maxPrecio = filtros.maxPrecio,
                minKm = filtros.minKm,
                maxKm = filtros.maxKm,
                etiqueta = filtros.etiqueta,
                cambio = filtros.cambio,
                minCuota = filtros.minCuota,
                maxCuota = filtros.maxCuota,
                minCv = filtros.minCv,
                maxCv = filtros.maxCv
            )
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error al filtrar coches", e)
            null
        }
    }

    suspend fun obtenerMarcas(): List<String> {
        return try {
            val response = RetrofitInstance.apiCoches.obtenerMarcas()
            if (response.isSuccessful) {
                response.body() ?: listOf("Audi", "BMW", "Mercedes") // Datos de prueba
            } else {
                listOf("Audi", "BMW", "Mercedes") // Datos de prueba si falla
            }
        } catch (e: Exception) {
            listOf("Audi", "BMW", "Mercedes") // Datos de prueba si hay error
        }
    }

    suspend fun obtenerModelos(): List<String> {
        return try {
            val response = RetrofitInstance.apiCoches.obtenerModelos()
            if (response.isSuccessful) response.body() ?: emptyList()
            else emptyList()
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error al obtener modelos", e)
            emptyList()
        }
    }

    suspend fun obtenerEtiquetas(): List<String> {
        return try {
            val response = RetrofitInstance.apiCoches.obtenerEtiquetas()
            if (response.isSuccessful) response.body() ?: emptyList()
            else emptyList()
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error al obtener etiquetas", e)
            emptyList()
        }
    }

    suspend fun obtenerCambios(): List<String> {
        return try {
            val response = RetrofitInstance.apiCoches.obtenerCambios()
            if (response.isSuccessful) response.body() ?: emptyList()
            else emptyList()
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error al obtener cambios", e)
            emptyList()
        }
    }
}
