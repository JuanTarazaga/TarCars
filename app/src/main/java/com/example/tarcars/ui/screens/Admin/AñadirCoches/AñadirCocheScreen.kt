package com.example.tarcars.ui.screens.Admin.AñadirCoches

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.tarcars.data.model.Coches
import java.util.*

@Composable
fun AñadirCocheScreen(
    navController: NavController,
    viewModel: AñadirCocheViewModel = hiltViewModel()
) {
    var titulo by remember { mutableStateOf("") }
    var imagenPrincipal by remember { mutableStateOf("") }
    var imagenesAdicionalesText by remember { mutableStateOf("") }
    var breveDescripcion by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var cuotaMensual by remember { mutableStateOf("") }
    var marca by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var anio by remember { mutableStateOf("") }
    var kilometraje by remember { mutableStateOf("") }
    var potencia by remember { mutableStateOf("") }
    var cambio by remember { mutableStateOf("") }
    var localizacion by remember { mutableStateOf("") }
    var etiqueta by remember { mutableStateOf("") }
    var etiquetaMedioAmbiental by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        @Composable
        fun campo(label: String, valor: String, onChange: (String) -> Unit) {
            OutlinedTextField(
                value = valor,
                onValueChange = onChange,
                label = { Text(label) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }

        Text("Imágenes", style = MaterialTheme.typography.titleSmall)

        if (imagenPrincipal.isNotBlank()) {
            AsyncImage(
                model = imagenPrincipal,
                contentDescription = "Imagen principal",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(bottom = 8.dp)
            )
        }

        campo("Imagen Principal (URL)", imagenPrincipal) { imagenPrincipal = it }

        campo("Imágenes Adicionales (separadas por coma)", imagenesAdicionalesText) { imagenesAdicionalesText = it }

        val imagenesList = imagenesAdicionalesText.split(",").map { it.trim() }.filter { it.isNotEmpty() }

        if (imagenesList.isNotEmpty()) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(imagenesList) { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp)
                            .padding(4.dp)
                    )
                }
            }
        }

        Text("Información", style = MaterialTheme.typography.titleSmall)
        campo("Título", titulo) { titulo = it }
        campo("Breve Descripción", breveDescripcion) { breveDescripcion = it }
        campo("Descripción", descripcion) { descripcion = it }
        campo("Precio", precio) { precio = it.filter { c -> c.isDigit() } }
        campo("Cuota Mensual", cuotaMensual) { cuotaMensual = it.filter { c -> c.isDigit() } }

        Text("Especificaciones", style = MaterialTheme.typography.titleSmall)
        campo("Marca", marca) { marca = it }
        campo("Modelo", modelo) { modelo = it }
        campo("Año", anio) { anio = it.filter { c -> c.isDigit() } }
        campo("Kilometraje", kilometraje) { kilometraje = it.filter { c -> c.isDigit() } }
        campo("Potencia", potencia) { potencia = it.filter { c -> c.isDigit() } }
        campo("Cambio", cambio) { cambio = it }

        Text("Ubicación y etiqueta", style = MaterialTheme.typography.titleSmall)
        campo("Localización", localizacion) { localizacion = it }
        campo("Etiqueta", etiqueta) { etiqueta = it }
        campo("Etiqueta Medioambiental", etiquetaMedioAmbiental) { etiquetaMedioAmbiental = it }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val nuevoCoche = Coches(
                    id = UUID.randomUUID().toString(),
                    titulo = titulo,
                    imagen_principal = imagenPrincipal,
                    imagenes_adicionales = imagenesList,
                    brevedescripcion = breveDescripcion,
                    descripcion = descripcion,
                    precio = precio.toIntOrNull() ?: 0,
                    cuotamensual = cuotaMensual.toIntOrNull() ?: 0,
                    marca = marca,
                    modelo = modelo,
                    año = anio.toIntOrNull() ?: 0,
                    kilometraje = kilometraje.toIntOrNull() ?: 0,
                    potencia = potencia.toIntOrNull() ?: 0,
                    cambio = cambio,
                    localizacion = localizacion,
                    etiqueta = etiqueta,
                    etiquetaMedioAmbiental = etiquetaMedioAmbiental
                )

                viewModel.insertarCoche(
                    coche = nuevoCoche,
                    onSuccess = { navController.popBackStack() },
                    onError = { /* mostrar error */ }
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Añadir Coche")
        }
    }
}
