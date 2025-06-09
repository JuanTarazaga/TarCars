package com.example.tarcars.ui.screens.Splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tarcars.R
import com.example.tarcars.ui.components.navigation.Destinations
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

@Composable
fun SplashScreen(navController: NavHostController) {
    Splash()

    LaunchedEffect(Unit) {
        delay(3000)
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            try {
                val firestore = FirebaseFirestore.getInstance()
                val doc = firestore.collection("usuarios").document(currentUser.uid).get().await()
                val rol = doc.getString("rol")

                val destino = if (rol == "admin") {
                    Destinations.ADMIN_SCREEN
                } else {
                    Destinations.VENTA_SCREEN
                }

                navController.navigate(destino) {
                    popUpTo(Destinations.SPLASH_SCREEN) { inclusive = true }
                }
            } catch (e: Exception) {
                // Maneja errores (ej. red o documento no encontrado)
                navController.navigate(Destinations.LOGIN_SCREEN) {
                    popUpTo(Destinations.SPLASH_SCREEN) { inclusive = true }
                }
            }
        } else {
            navController.navigate(Destinations.LOGIN_SCREEN) {
                popUpTo(Destinations.SPLASH_SCREEN) { inclusive = true }
            }
        }
    }
}

    @Composable
fun Splash() {
    // degradado lineal
    val gradient = Brush.linearGradient(
        colors = listOf(Color(0xFF000000), Color(0xFF737373), Color(0xFF333333)),
        start = androidx.compose.ui.geometry.Offset.Zero,
        end = androidx.compose.ui.geometry.Offset.Infinite
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo TarCars",
            modifier = Modifier
                .size(140.dp)
                .clip(RoundedCornerShape(18.dp)))
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(navController = rememberNavController())
}