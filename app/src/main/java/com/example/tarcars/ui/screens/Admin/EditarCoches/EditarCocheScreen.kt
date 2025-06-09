package com.example.tarcars.ui.screens.Admin.EditarCoches

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.tarcars.data.model.Coches

@Composable
fun EditarCocheScreen(
    navController: NavController,
    cocheId: String,
    viewModel: EditarCocheViewModel = hiltViewModel()
) {
    val coche by viewModel.coche.collectAsState()
    val cargando by viewModel.cargando.collectAsState()

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

    LaunchedEffect(cocheId) {
        viewModel.cargarCochePorId(cocheId)
    }

    LaunchedEffect(coche) {
        coche?.let {
            titulo = it.titulo
            imagenPrincipal = it.imagen_principal
            imagenesAdicionalesText = it.imagenes_adicionales.joinToString(", ")
            breveDescripcion = it.brevedescripcion
            descripcion = it.descripcion
            precio = it.precio.toString()
            cuotaMensual = it.cuotamensual.toString()
            marca = it.marca
            modelo = it.modelo
            anio = it.año.toString()
            kilometraje = it.kilometraje.toString()
            potencia = it.potencia.toString()
            cambio = it.cambio
            localizacion = it.localizacion
            etiqueta = it.etiqueta
            etiquetaMedioAmbiental = it.etiquetaMedioAmbiental
        }
    }

    if (cargando) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        coche?.let { cocheActual ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // IMÁGENES
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

                OutlinedTextField(
                    value = imagenPrincipal,
                    onValueChange = { imagenPrincipal = it },
                    label = { Text("URL de imagen principal") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = imagenesAdicionalesText,
                    onValueChange = { imagenesAdicionalesText = it },
                    label = { Text("URLs de imágenes adicionales (separadas por coma)") },
                    modifier = Modifier.fillMaxWidth()
                )

                val imagenesList = imagenesAdicionalesText.split(",").map { it.trim() }.filter { it.isNotEmpty() }

                if (imagenesList.isNotEmpty()) {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(imagenesList) { url ->
                            AsyncImage(
                                model = url,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .padding(4.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }

                // INFORMACIÓN GENERAL
                Text("Información", style = MaterialTheme.typography.titleSmall)
                OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = breveDescripcion, onValueChange = { breveDescripcion = it }, label = { Text("Breve descripción") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") }, modifier = Modifier.fillMaxWidth())

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = precio,
                        onValueChange = { precio = it.filter(Char::isDigit) },
                        label = { Text("Precio") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = cuotaMensual,
                        onValueChange = { cuotaMensual = it.filter(Char::isDigit) },
                        label = { Text("Cuota mensual") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                // ESPECIFICACIONES
                Text("Especificaciones", style = MaterialTheme.typography.titleSmall)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = marca, onValueChange = { marca = it }, label = { Text("Marca") }, modifier = Modifier.weight(1f))
                    OutlinedTextField(value = modelo, onValueChange = { modelo = it }, label = { Text("Modelo") }, modifier = Modifier.weight(1f))
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = anio, onValueChange = { anio = it.filter(Char::isDigit) }, label = { Text("Año") }, modifier = Modifier.weight(1f), singleLine = true)
                    OutlinedTextField(value = kilometraje, onValueChange = { kilometraje = it.filter(Char::isDigit) }, label = { Text("Kilómetros") }, modifier = Modifier.weight(1f), singleLine = true)
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = potencia, onValueChange = { potencia = it.filter(Char::isDigit) }, label = { Text("Potencia") }, modifier = Modifier.weight(1f), singleLine = true)
                    OutlinedTextField(value = cambio, onValueChange = { cambio = it }, label = { Text("Cambio") }, modifier = Modifier.weight(1f))
                }

                // UBICACIÓN Y ETIQUETA
                Text("Ubicación y etiqueta", style = MaterialTheme.typography.titleSmall)
                OutlinedTextField(value = localizacion, onValueChange = { localizacion = it }, label = { Text("Localización") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = etiqueta, onValueChange = { etiqueta = it }, label = { Text("Etiqueta") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = etiquetaMedioAmbiental, onValueChange = { etiquetaMedioAmbiental = it }, label = { Text("Etiqueta medioambiental") }, modifier = Modifier.fillMaxWidth())

                Spacer(modifier = Modifier.height(16.dp))

                // BOTÓN
                Button(
                    onClick = {
                        val cocheEditado = cocheActual.copy(
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

                        viewModel.actualizarCoche(
                            coche = cocheEditado,
                            onSuccess = { navController.popBackStack() },
                            onError = { /* Mostrar error */ }
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar cambios")
                }
            }
        } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No se encontró el coche")
        }
    }
}
