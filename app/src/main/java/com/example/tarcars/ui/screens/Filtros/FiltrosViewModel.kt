package com.example.tarcars.ui.screens.Filtros

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarcars.data.model.Coches
import com.example.tarcars.data.model.Filtros
import com.example.tarcars.data.repositories.CochesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FiltrosViewModel @Inject constructor(
    private val repository: CochesRepository
) : ViewModel() {

    var filtros by mutableStateOf(Filtros())
        private set

    var marcas by mutableStateOf<List<String>>(emptyList())
        private set
    var modelos by mutableStateOf<List<String>>(emptyList())
        private set
    var etiquetas by mutableStateOf<List<String>>(emptyList())
        private set
    var cambios by mutableStateOf<List<String>>(emptyList())
        private set

    var cochesEncontrados by mutableStateOf<List<Coches>?>(null)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    init {
        cargarFiltrosDesdeApi()
        actualizarCochesFiltrados()
    }

    private fun cargarFiltrosDesdeApi() {
        viewModelScope.launch {
            try {
                marcas = repository.obtenerMarcas().also {
                    Log.d("FILTROS", "Marcas recibidas: ${it.size}")
                }
                modelos = repository.obtenerModelos().also {
                    Log.d("FILTROS", "Modelos recibidos: ${it.size}")
                }
                etiquetas = repository.obtenerEtiquetas().also {
                    Log.d("FILTROS", "Etiquetas recibidas: ${it.size}")
                }
                cambios = repository.obtenerCambios().also {
                    Log.d("FILTROS", "Cambios recibidos: ${it.size}")
                }
            } catch (e: Exception) {
                Log.e("FILTROS", "Error al cargar filtros", e)
            }
        }
    }

    private fun actualizarCochesFiltrados() {
        viewModelScope.launch {
            cochesEncontrados = repository.getCochesFiltrados(filtros)
        }
    }

    fun actualizarMarca(marca: String?) {
        filtros = filtros.copy(marca = marca)
        actualizarCochesFiltrados()
    }

    fun actualizarModelo(modelo: String?) {
        filtros = filtros.copy(modelo = modelo)
        actualizarCochesFiltrados()
    }

    fun actualizarPrecio(min: Int?, max: Int?) {
        filtros = filtros.copy(minPrecio = min, maxPrecio = max)
        actualizarCochesFiltrados()
    }

    fun actualizarKm(min: Int?, max: Int?) {
        filtros = filtros.copy(minKm = min, maxKm = max)
        actualizarCochesFiltrados()
    }

    fun actualizarEtiqueta(etiqueta: String?) {
        filtros = filtros.copy(etiqueta = etiqueta)
        actualizarCochesFiltrados()
    }

    fun actualizarCambio(cambio: String?) {
        filtros = filtros.copy(cambio = cambio)
        actualizarCochesFiltrados()
    }

    fun actualizarCuota(min: Int?, max: Int?) {
        filtros = filtros.copy(minCuota = min, maxCuota = max)
        actualizarCochesFiltrados()
    }

    fun actualizarPotencia(min: Int?, max: Int?) {
        filtros = filtros.copy(minCv = min, maxCv = max)
        actualizarCochesFiltrados()
    }

}


