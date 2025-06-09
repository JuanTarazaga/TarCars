package com.example.tarcars.ui.screens.Registro

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tarcars.R
import com.example.tarcars.ui.components.navigation.Destinations

@Composable
fun RegistroScreen(
    navController: NavHostController,
    viewModel: RegistroViewModel = hiltViewModel()
) {
    val state by viewModel.state
    val context = LocalContext.current
    val passwordVisible = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(Color(0xFF000000), Color(0xFF555555), Color(0xFF333333))
                )
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo TarCars",
            modifier = Modifier
                .padding(top = 25.dp)
                .size(140.dp)
                .clip(RoundedCornerShape(18.dp))
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "CREA UNA NUEVA CUENTA",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        TextButton(onClick = {
            navController.navigate(Destinations.LOGIN_SCREEN) {
                launchSingleTop = true
            }
        }) {
            Text(
                text = "¿Ya estás registrado? Inicia sesión aquí",
                fontWeight = FontWeight.W300,
                color = Color.White,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.padding(vertical = 20.dp)
            )
        }

        OutlinedTextField(
            value = state.name,
            onValueChange = { viewModel.onNameChange(it) },
            label = { Text("Nombre", color = Color.Gray) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = state.email,
            onValueChange = { viewModel.onEmailChange(it) },
            label = { Text("Email", color = Color.Gray) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = state.phone,
            onValueChange = { viewModel.onPhoneChange(it) },
            label = { Text("Teléfono", color = Color.Gray) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Contraseña", color = Color.Gray) },
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val icon = if (passwordVisible.value) R.drawable.ojo_abierto else R.drawable.ojo_cerrado
                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = "Toggle Password",
                        tint = Color.Gray
                    )
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth().padding(bottom = 40.dp),
            singleLine = true
        )

        TextButton(
            onClick = {
                errorMessage.value = ""
                if (state.email.contains(" ")) {
                    errorMessage.value = "El correo no puede contener espacios"
                    return@TextButton
                }
                if (state.password.length < 6) {
                    errorMessage.value = "La contraseña debe tener al menos 6 caracteres"
                    return@TextButton
                }

                viewModel.register {
                    Toast.makeText(context, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    navController.navigate(Destinations.LOGIN_SCREEN)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Color.White, RoundedCornerShape(18.dp))
                .padding(8.dp)
        ) {
            Text(text = "Registrarse", color = Color.White, fontSize = 16.sp)
        }

        if (errorMessage.value.isNotEmpty()) {
            Text(
                text = errorMessage.value,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    RegistroScreen(navController = rememberNavController())
}
