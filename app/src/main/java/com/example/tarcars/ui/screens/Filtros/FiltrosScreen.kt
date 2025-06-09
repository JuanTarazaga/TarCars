package com.example.tarcars.ui.screens.Filtros

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tarcars.data.model.Coches
import com.example.tarcars.ui.components.MenuDesplegable
import com.example.tarcars.ui.components.navigation.Destinations
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun FiltrosScreen(
    navController: NavController,
    filtrosViewModel: FiltrosViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    // Estados de filtros
    var minPrecio by remember { mutableStateOf(0f) }
    var maxPrecio by remember { mutableStateOf(100000f) }
    var minKm by remember { mutableStateOf(0f) }
    var maxKm by remember { mutableStateOf(200000f) }
    var minCuota by remember { mutableStateOf(0f) }
    var maxCuota by remember { mutableStateOf(2000f) }
    var minCv by remember { mutableStateOf(0f) }
    var maxCv by remember { mutableStateOf(800f) }

    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var etiqueta by remember { mutableStateOf("") }
    var cambio by remember { mutableStateOf("") }

    val cochesEncontrados = filtrosViewModel.cochesEncontrados

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Marca", style = MaterialTheme.typography.labelLarge)
        MenuDesplegable(value = marca, options = filtrosViewModel.marcas) {
            marca = it
            filtrosViewModel.actualizarMarca(it)
        }

        Text("Modelo", style = MaterialTheme.typography.labelLarge)
        MenuDesplegable(value = modelo, options = filtrosViewModel.modelos) {
            modelo = it
            filtrosViewModel.actualizarModelo(it)
        }

        Text("Precio: ${minPrecio.toInt()}€ - ${maxPrecio.toInt()}€")
        RangeSlider(
            value = minPrecio..maxPrecio,
            onValueChange = {
                minPrecio = it.start
                maxPrecio = it.endInclusive
                filtrosViewModel.actualizarPrecio(minPrecio.toInt(), maxPrecio.toInt())
            },
            valueRange = 0f..100000f
        )

        Text("Kilometraje: ${minKm.toInt()} km - ${maxKm.toInt()} km")
        RangeSlider(
            value = minKm..maxKm,
            onValueChange = {
                minKm = it.start
                maxKm = it.endInclusive
                filtrosViewModel.actualizarKm(minKm.toInt(), maxKm.toInt())
            },
            valueRange = 0f..200000f
        )

        Text("Cuota Mensual: ${minCuota.toInt()}€ - ${maxCuota.toInt()}€")
        RangeSlider(
            value = minCuota..maxCuota,
            onValueChange = {
                minCuota = it.start
                maxCuota = it.endInclusive
                filtrosViewModel.actualizarCuota(minCuota.toInt(), maxCuota.toInt())
            },
            valueRange = 0f..2000f
        )

        Text("Potencia: ${minCv.toInt()} CV - ${maxCv.toInt()} CV")
        RangeSlider(
            value = minCv..maxCv,
            onValueChange = {
                minCv = it.start
                maxCv = it.endInclusive
                filtrosViewModel.actualizarPotencia(minCv.toInt(), maxCv.toInt())
            },
            valueRange = 0f..800f
        )

        Text("Etiqueta", style = MaterialTheme.typography.labelLarge)
        MenuDesplegable(value = etiqueta, options = filtrosViewModel.etiquetas) {
            etiqueta = it
            filtrosViewModel.actualizarEtiqueta(it)
        }

        Text("Cambio", style = MaterialTheme.typography.labelLarge)
        MenuDesplegable(value = cambio, options = filtrosViewModel.cambios) {
            cambio = it
            filtrosViewModel.actualizarCambio(it)
        }

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        when {
            cochesEncontrados == null -> Text("Cargando resultados...")
            cochesEncontrados.isEmpty() -> Text(
                "No hay coches que coincidan con la búsqueda.",
                color = MaterialTheme.colorScheme.error
            )
            else -> Text("Coches encontrados: ${cochesEncontrados.size}")
        }

        Button(
            onClick = {
                scope.launch {
                    cochesEncontrados?.let { coches ->
                        if (coches.isNotEmpty()) {
                            val json = URLEncoder.encode(
                                Gson().toJson(coches),
                                StandardCharsets.UTF_8.toString()
                            )
                            navController.navigate("${Destinations.VENTA_SCREEN}?cochesJson=$json")
                        }
                    }
                }
            },
            enabled = !cochesEncontrados.isNullOrEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .alpha(if (!cochesEncontrados.isNullOrEmpty()) 1f else 0.5f)
        ) {
            Text("Buscar")
        }
    }
}
