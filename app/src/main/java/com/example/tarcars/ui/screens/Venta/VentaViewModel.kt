package com.example.tarcars.ui.screens.Venta

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarcars.data.model.Coches
import com.example.tarcars.data.repositories.CochesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VentaViewModel @Inject constructor(
    private val cochesRepository: CochesRepository
) : ViewModel() {
    var listaCoches by mutableStateOf<List<Coches>>(emptyList())
        private set
    var cargando by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    init {
        cargarCoches()
    }

    fun cargarCoches() {
        viewModelScope.launch {
            cargando = true
            try {
                val resultado = cochesRepository.getCoches()
                if (resultado != null) {
                    listaCoches = resultado
                } else {
                    error = "No se pudieron cargar los coches"
                }
            } catch (e: Exception) {
                error = e.message ?: "Error desconocido"
            } finally {
                cargando = false
            }
        }
    }
}