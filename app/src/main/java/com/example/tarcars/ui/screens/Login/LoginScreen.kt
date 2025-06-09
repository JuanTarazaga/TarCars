package com.example.tarcars.ui.screens.Login

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
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state
    val passwordVisible = remember { mutableStateOf(false) }
    val errorMessage = remember { mutableStateOf<String?>(null) }

    val gradient = Brush.linearGradient(
        colors = listOf(Color(0xFF000000), Color(0xFF555555), Color(0xFF333333))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
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
            text = "INICIAR SESIÓN",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        TextButton(onClick = {
            navController.navigate(Destinations.REGISTRO_SCREEN) {
                launchSingleTop = true
            }
        }) {
            Text(
                text = "¿No tienes cuenta? Regístrate aquí",
                fontWeight = FontWeight.W300,
                color = Color.White,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.padding(bottom = 40.dp)
            )
        }

        OutlinedTextField(
            value = state.email,
            onValueChange = {
                viewModel.onEmailChange(it)
                errorMessage.value = null
            },
            label = { Text("Email", color = Color.Gray) },
            isError = state.email.contains(" "),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )

        OutlinedTextField(
            value = state.password,
            onValueChange = { viewModel.onPasswordChange(it) },
            label = { Text("Contraseña", color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp),
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
            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true
        )

        TextButton(
            onClick = {
                navController.navigate(Destinations.RECUPERAR_CONTRASEÑA_SCREEN)
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "¿Has olvidado tu contraseña?",
                textDecoration = TextDecoration.Underline,
                color = Color.White,
                fontWeight = FontWeight.Medium
            )
        }

        TextButton(
            onClick = {
                if (state.email.contains(" ")) {
                    errorMessage.value = "El correo no debe contener espacios"
                } else if (state.email.isBlank() || state.password.isBlank()) {
                    errorMessage.value = "Por favor, completa todos los campos"
                } else {
                    viewModel.login { rol ->
                        val destino = if (rol == "admin") {
                            Destinations.ADMIN_SCREEN
                        } else {
                            Destinations.VENTA_SCREEN
                        }

                        navController.navigate(destino) {
                            popUpTo(Destinations.LOGIN_SCREEN) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .border(2.dp, Color.White, RoundedCornerShape(18.dp))
                .padding(8.dp)
        ) {
            Text(text = "Iniciar sesión", color = Color.White, fontSize = 16.sp)
        }

        errorMessage.value?.let {
            Text(
                text = it,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController())
}
