package com.example.tarcars.ui.screens.Admin.AñadirCoches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tarcars.data.model.Coches
import com.example.tarcars.data.repositories.CochesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AñadirCocheViewModel @Inject constructor(
    private val repository: CochesRepository
) : ViewModel() {

    fun insertarCoche(
        coche: Coches,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        viewModelScope.launch {
            try {
                repository.postCoche(coche)
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}
