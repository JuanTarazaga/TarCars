package com.example.tarcars.ui.screens.Admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarcars.data.model.Coches
import com.example.tarcars.data.repositories.CochesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val repository: CochesRepository
) : ViewModel() {

    private val _listaCoches = MutableStateFlow<List<Coches>>(emptyList())
    val listaCoches: StateFlow<List<Coches>> = _listaCoches

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _mensajeError = MutableStateFlow<String?>(null)
    val mensajeError: StateFlow<String?> = _mensajeError

    fun obtenerTodosLosCoches() {
        viewModelScope.launch {
            _cargando.value = true
            _mensajeError.value = null
            try {
                val coches = repository.getCoches()
                if (coches != null) {
                    _listaCoches.value = coches
                } else {
                    _mensajeError.value = "No se pudo conectar con la API"
                }
            } catch (e: Exception) {
                _mensajeError.value = "Error inesperado: ${e.message}"
            } finally {
                _cargando.value = false
            }
        }
    }

    fun eliminarCoche(id: String) {
        viewModelScope.launch {
            repository.deleteCoche(id)
            obtenerTodosLosCoches()
        }
    }
}
