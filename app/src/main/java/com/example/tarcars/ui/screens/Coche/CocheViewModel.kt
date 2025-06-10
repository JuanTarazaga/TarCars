package com.example.tarcars.ui.screens.Coche

import androidx.compose.runtime.collectAsState
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
class CocheViewModel @Inject constructor(
    private val cochesRepository: CochesRepository
) : ViewModel() {

    private val _coche = MutableStateFlow<Coches?>(null)
    val coche: StateFlow<Coches?> = _coche

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun getCoche(id: String) {
        viewModelScope.launch {
            _cargando.value = true
            try {
                val cocheResponse = cochesRepository.getCoche(id)
                _coche.value = cocheResponse
                if (cocheResponse == null) {
                    _error.value = "No se encontró el coche con id $id"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
            } finally {
                _cargando.value = false
            }
        }
    }
}
