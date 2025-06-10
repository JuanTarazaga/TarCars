package com.example.tarcars.ui.screens.Coche

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.tarcars.R
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.window.Dialog
import com.example.tarcars.data.model.Coches
import com.example.tarcars.ui.theme.TarCarsTheme
//import kotlinx.coroutines.flow.internal.NoOpContinuation.context
import com.example.tarcars.ui.components.PopUpLlamar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocheScreen(
    navController: NavController,
    viewModel: CocheViewModel = hiltViewModel()
) {
    val id = remember {
        navController.currentBackStackEntry?.arguments?.getString("id")
    }

    LaunchedEffect(id) {
        id?.let { viewModel.getCoche(it) }
    }

    val coche by viewModel.coche.collectAsState()
    val cargando by viewModel.cargando.collectAsState()

    var mostrarPopupLlamar by remember { mutableStateOf(false) }

    if (cargando) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }

    } else if (coche == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No se encontraron datos del coche")
        }

    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
                    CarruselImagenes(
                        imagenes = listOf(coche!!.imagen_principal) + coche!!.imagenes_adicionales
                    )
                }
            }

            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = coche!!.titulo,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        InfoItem1("${coche!!.kilometraje} KM", R.drawable.kilometraje)
                        InfoItem1(coche!!.año.toString(), R.drawable.calendario)
                        InfoItem1(coche!!.localizacion, R.drawable.localizacion)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        InfoItem1(coche!!.cambio, R.drawable.caja_de_cambios)
                        InfoItem1("${coche!!.potencia}CV", R.drawable.velocimetro)
                        InfoItem1(coche!!.etiquetaMedioAmbiental, R.drawable.etiquetasmedioambientales3)
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${coche!!.precio}€",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFFDE59)
                        )
                        Text(
                            text = "Desde ${coche!!.cuotamensual}€ / Mes",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Button(
                        onClick = { mostrarPopupLlamar = true },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFDE59),
                            contentColor = Color.Black
                        )
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.llamar),
                                contentDescription = "Llamar",
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "LLAMAR", color = Color.Black)
                        }
                    }

                    if (mostrarPopupLlamar) {
                        PopUpLlamar(numero = "911123456") {
                            mostrarPopupLlamar = false
                        }
                    }

                    Text(
                        text = "DESCRIPCIÓN",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    Text(
                        text = coche!!.descripcion,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun InfoItem1(texto: String, icono: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(100.dp)
    ) {
        if (icono == R.drawable.etiquetasmedioambientales3) {
            Icon(
                painter = painterResource(id = icono),
                contentDescription = texto,
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )
        } else {
            Icon(
                painter = painterResource(id = icono),
                contentDescription = texto,
                modifier = Modifier.size(24.dp),
                tint = Color(0xFF424242)
            )
        }

        Text(
            text = texto,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 4.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CarruselImagenes(imagenes: List<String>) {
    var imagenActual by remember { mutableStateOf(0) }
    var mostrarDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { mostrarDialog = true }
    ) {
        AsyncImage(
            model = imagenes[imagenActual],
            contentDescription = "Imagen del coche",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.coche_carga),
            error = painterResource(R.drawable.no_coche)
        )

        if (imagenActual > 0) {
            IconButton(
                onClick = { imagenActual-- },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.4f), CircleShape)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_left),
                    contentDescription = "Anterior",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        if (imagenActual < imagenes.size - 1) {
            IconButton(
                onClick = { imagenActual++ },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp)
                    .background(Color.Black.copy(alpha = 0.4f), CircleShape)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right),
                    contentDescription = "Siguiente",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(imagenes.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(8.dp)
                        .background(
                            color = if (index == imagenActual) Color(0xFFFFDE59)
                            else Color.White.copy(alpha = 0.5f),
                            shape = CircleShape
                        )
                )
            }
        }
    }

    if (mostrarDialog) {
        Dialog(onDismissRequest = { mostrarDialog = false }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imagenes[imagenActual],
                    contentDescription = "Imagen ampliada",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.85f)
                )

                IconButton(
                    onClick = { mostrarDialog = false },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = "Cerrar",
                        tint = Color.White
                    )
                }

                if (imagenActual > 0) {
                    IconButton(
                        onClick = { imagenActual-- },
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(16.dp)
                            .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_left),
                            contentDescription = "Anterior",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                if (imagenActual < imagenes.size - 1) {
                    IconButton(
                        onClick = { imagenActual++ },
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(16.dp)
                            .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_right),
                            contentDescription = "Siguiente",
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CocheScreenPreview() {

}