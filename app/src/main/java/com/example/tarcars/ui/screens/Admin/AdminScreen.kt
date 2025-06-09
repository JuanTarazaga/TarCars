package com.example.tarcars.ui.screens.Admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tarcars.R
import com.example.tarcars.ui.components.CocheAdminItem
import com.example.tarcars.ui.components.navigation.Destinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
    navController: NavController,
    viewModel: AdminViewModel = hiltViewModel()
) {
    val coches by viewModel.listaCoches.collectAsState()
    val cargando by viewModel.cargando.collectAsState()
    val mensajeError by viewModel.mensajeError.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.obtenerTodosLosCoches()
    }

    Scaffold(
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp)
            ) {
                FloatingActionButton(
                    onClick = { navController.navigate(Destinations.COMUNIDAD_ADMIN_SCREEN) },
                    modifier = Modifier.size(60.dp),
                    containerColor = Color.Transparent,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.comunidad),
                        contentDescription = "Comunidad",
                        tint = Color.Unspecified
                    )
                }

                FloatingActionButton(
                    onClick = { navController.navigate(Destinations.AÑADIR_COCHE_SCREEN) },
                    modifier = Modifier.size(60.dp),
                    containerColor = Color.Transparent,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.anadir),
                        contentDescription = "Añadir",
                        tint = Color.Unspecified
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()) {

            Text(
                text = "PANEL DE ADMINISTRADOR",
                fontSize = 22.sp,
                fontWeight = FontWeight.W500,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .align(Alignment.CenterHorizontally)
            )

            if (mensajeError != null) {
                Text(
                    text = mensajeError!!,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }

            if (cargando) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn {
                    items(coches) { coche ->
                        CocheAdminItem(
                            coche = coche,
                            onEditar = { navController.navigate("${Destinations.EDITAR_COCHE_SCREEN}/${coche.id}") },
                            onEliminar = { viewModel.eliminarCoche(coche.id) }
                        )
                    }
                }
            }
        }
    }
}
