package com.example.tarcars.ui.screens.Comunidad

import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComunidadScreen(navController: NavController) {
    val viewModel: ComunidadViewModel = hiltViewModel()
    val context = LocalContext.current

    val baneado by viewModel.baneado.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()
    val enviando by viewModel.enviando.collectAsState()
    val mensajes by viewModel.mensajes.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .imePadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "COMUNIDAD",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxSize(),
                shape = RoundedCornerShape(24.dp),
                color = Color(0xFFBFBFBF)
            ) {
                if (!baneado) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(mensajes) { msg ->
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(25),
                                color = Color(0xFFE6E6CC)
                            ) {
                                Text(
                                    text = "${msg.autor}: ${msg.contenido}",
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }

            if (baneado) {
                Surface(
                    modifier = Modifier
                        .matchParentSize(),
                    shape = RoundedCornerShape(24.dp),
                    color = Color(0xAA383838)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "Has sido baneado del chat.\nNo puedes ver ni enviar mensajes.",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(24.dp)
                        )
                    }
                }
            }
        }

        OutlinedTextField(
            value = mensaje,
            onValueChange = viewModel::onMensajeChange,
            label = { Text("MENSAJE") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            maxLines = 3,
            shape = RoundedCornerShape(5.dp),
            enabled = !baneado,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF0F0F0),
                unfocusedContainerColor = Color(0xFFF0F0F0),
                disabledContainerColor = Color(0xFFE0E0E0),
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            )
        )

        Button(
            onClick = { viewModel.enviarMensaje() },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            enabled = mensaje.isNotBlank() && !enviando && !baneado,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (!baneado) Color(0xFF444444) else Color.Gray,
                contentColor = Color.White
            )
        ) {
            if (enviando) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text("ENVIAR", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
