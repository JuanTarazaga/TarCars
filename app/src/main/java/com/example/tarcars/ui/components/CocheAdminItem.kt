package com.example.tarcars.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import coil.compose.rememberAsyncImagePainter
import com.example.tarcars.data.model.Coches
import com.example.tarcars.R

@Composable
fun CocheAdminItem(
    coche: Coches,
    onEditar: () -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFC7C7C7)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Imagen del coche
            Image(
                painter = rememberAsyncImagePainter(model = coche.imagen_principal),
                contentDescription = "Imagen del coche",
                modifier = Modifier
                    .size(100.dp)
                    .aspectRatio(1f)
                    .padding(4.dp)
            )

            // Info y acciones
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "${coche.marca} ${coche.modelo}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Año: ${coche.año}", style = MaterialTheme.typography.bodySmall)
                Text(text = "KM: ${coche.kilometraje}", style = MaterialTheme.typography.bodySmall)
                Text(text = "ID: ${coche.id}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }

            // Botones de acción
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(onClick = onEditar) {
                    Icon(
                        painter = painterResource(id = R.drawable.boton_editar),
                        contentDescription = "Editar",
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(onClick = onEliminar) {
                    Icon(
                        painter = painterResource(id = R.drawable.papelera),
                        contentDescription = "Eliminar",
                        tint = Color.Red,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
