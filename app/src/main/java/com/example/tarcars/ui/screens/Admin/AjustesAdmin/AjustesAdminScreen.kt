package com.example.tarcars.ui.screens.Admin.AjustesAdmin

import android.app.AlertDialog
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tarcars.ui.components.navigation.Destinations
import com.example.tarcars.ui.screens.Ajustes.EditableField
import com.example.tarcars.ui.screens.Ajustes.PasswordField
import com.example.tarcars.ui.screens.Ajustes.ReadOnlyField

@Composable
fun AjustesAdminScreen(navController: NavController) {
    val viewModel: AjustesAdminViewModel = hiltViewModel()
    val context = LocalContext.current

    val correo = viewModel.correo.collectAsState().value
    var passwordActual by remember { mutableStateOf("") }
    var nuevaPassword by remember { mutableStateOf("") }
    var confirmarPassword by remember { mutableStateOf("") }

    val nombre = viewModel.nombre.collectAsState().value
    val telefono = viewModel.telefono.collectAsState().value

    var editableNombre by remember { mutableStateOf("") }
    var editableTelefono by remember { mutableStateOf("") }

    var correoEliminar by remember { mutableStateOf("") }
    var correoAsignarAdmin by remember { mutableStateOf("") }

    LaunchedEffect(nombre, telefono) {
        editableNombre = nombre
        editableTelefono = telefono
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .navigationBarsPadding()
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Mi Cuenta", fontSize = 28.sp, fontWeight = FontWeight.Bold)

        Surface(
            shape = RoundedCornerShape(8.dp),
            border = ButtonDefaults.outlinedButtonBorder,
            modifier = Modifier.fillMaxWidth(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                EditableField("NOMBRE Y APELLIDOS", editableNombre) { editableNombre = it }
                ReadOnlyField("CORREO ELECTRÓNICO", correo)
                PasswordField("CONTRASEÑA ACTUAL", passwordActual) { passwordActual = it }
                PasswordField("NUEVA CONTRASEÑA", nuevaPassword) { nuevaPassword = it }
                PasswordField("CONFIRMAR CONTRASEÑA", confirmarPassword) { confirmarPassword = it }
                EditableField("TELÉFONO", editableTelefono) { editableTelefono = it }
            }
        }

        Button(
            onClick = {
                if (nuevaPassword.isNotBlank() && nuevaPassword != confirmarPassword) {
                    Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
                    return@Button
                }
                viewModel.actualizarDatos(
                    editableNombre,
                    editableTelefono,
                    passwordActual,
                    nuevaPassword
                ) { mensaje ->
                    Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier.fillMaxWidth().height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9A8383)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("GUARDAR", fontWeight = FontWeight.Bold)
        }

        Button(
            onClick = {
                viewModel.cerrarSesion(navController)
            },
            modifier = Modifier.fillMaxWidth().height(55.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4C4C)),
            shape = RoundedCornerShape(50)
        ) {
            Text("CERRAR SESIÓN", color = Color.White, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text("Administrador", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        Surface(
            shape = RoundedCornerShape(8.dp),
            border = ButtonDefaults.outlinedButtonBorder,
            modifier = Modifier.fillMaxWidth(),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                EditableField("CORREO DEL USUARIO A BANEAR/DESBANEAR", correoEliminar) {
                    correoEliminar = it
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            viewModel.banearUsuarioPorCorreo(correoEliminar) {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA500)) // naranja
                    ) {
                        Text("BANEAR", color = Color.White, fontWeight = FontWeight.Bold)
                    }

                    Button(
                        onClick = {
                            viewModel.desbanearUsuarioPorCorreo(correoEliminar) {
                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // verde
                    ) {
                        Text("DESBANEAR", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }

                EditableField("CORREO ELECTRÓNICO PARA HACER ADMIN", correoAsignarAdmin) {
                    correoAsignarAdmin = it
                }
                Button(
                    onClick = {
                        viewModel.asignarAdminPorCorreo(correoAsignarAdmin) {
                            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text("ASIGNAR ADMIN", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
