package com.example.tarcars.ui.screens.Admin.EditarCoches

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
class EditarCocheViewModel @Inject constructor(
    private val repository: CochesRepository
) : ViewModel() {

    private val _coche = MutableStateFlow<Coches?>(null)
    val coche: StateFlow<Coches?> = _coche

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    fun cargarCochePorId(id: String) {
        viewModelScope.launch {
            _cargando.value = true
            try {
                val resultado = repository.getCoche(id)
                _coche.value = resultado
            } finally {
                _cargando.value = false
            }
        }
    }

    fun actualizarCoche(coche: Coches, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                repository.putCoche(coche)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Error desconocido")
            }
        }
    }
}
