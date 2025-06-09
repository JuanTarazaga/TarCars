package com.example.tarcars.ui.screens.Venta

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.tarcars.R
import com.example.tarcars.data.model.Coches
import com.example.tarcars.ui.components.PopUpLlamar
import com.example.tarcars.ui.components.navigation.Destinations
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun VentaScreen(
    navController: NavController,
    navBackStackEntry: NavBackStackEntry,
    viewModel: VentaViewModel = hiltViewModel()
) {
    val cochesDesdeFiltro: List<Coches>? = remember {
        navBackStackEntry.arguments?.getString("cochesJson")?.let { jsonEncoded ->
            val json = URLDecoder.decode(jsonEncoded, StandardCharsets.UTF_8.toString())
            try {
                val type = object : TypeToken<List<Coches>>() {}.type
                Gson().fromJson<List<Coches>>(json, type)
            } catch (e: Exception) {
                null
            }
        }
    }

    val listaCoches = cochesDesdeFiltro ?: viewModel.listaCoches
    val cargando = viewModel.cargando
    val error = viewModel.error

    if (cochesDesdeFiltro == null && cargando) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (cochesDesdeFiltro == null && error != null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Error: $error")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "COCHES A LA VENTA",
                        modifier = Modifier.padding(vertical = 8.dp),
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF424242)
                    )

                    IconButton(
                        onClick = { navController.navigate("${Destinations.FILTROS_SCREEN}") },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.filtros),
                            contentDescription = "Filtros",
                            tint = Color(0xFF424242)
                        )
                    }
                }
            }

            items(listaCoches) { coche ->
                ItemCoche(coche = coche) {
                    navController.navigate("${Destinations.COCHE_SCREEN}/${coche.id}")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemCoche(coche: Coches, onClick: () -> Unit) {
    var mostrarPopupLlamar by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = coche.imagen_principal,
                contentDescription = coche.titulo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.coche_carga),
                error = painterResource(R.drawable.no_coche)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text("${coche.marca} ${coche.modelo}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("${coche.kilometraje} KM")
                Text(coche.año.toString())
                Text(coche.localizacion)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(coche.brevedescripcion, fontWeight = FontWeight.SemiBold)
                Badge(containerColor = Color(0xFFFFDE59), contentColor = Color.Black) {
                    Text(coche.etiqueta)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("${coche.precio}€", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("Desde ${coche.cuotamensual}€/Mes", fontSize = 16.sp)
                }

                Button(
                    onClick = { mostrarPopupLlamar = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFDE59))
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.llamar),
                            contentDescription = "Llamar",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("LLAMAR", color = Color.Black)
                    }
                }

                if (mostrarPopupLlamar) {
                    PopUpLlamar(numero = "911123456") {
                        mostrarPopupLlamar = false
                    }
                }
            }
        }
    }
}
