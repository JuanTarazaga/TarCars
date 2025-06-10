package com.example.tarcars

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tarcars.ui.components.navigation.Destinations
import com.example.tarcars.ui.components.navigation.Destinations.getBottomBarForRoute
import com.example.tarcars.ui.components.navigation.Destinations.getTopBarForRoute
import com.example.tarcars.ui.components.navigation.NavGraph
import com.example.tarcars.ui.theme.TarCarsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TarCarsTheme {
               Surface {
                   App1()
               }
            }
        }
    }
}

@Composable
fun App1(){
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    val showFAB = remember { mutableStateOf(true) }

    val hideFABDestinations = listOf(
        Destinations.SPLASH_SCREEN,
        Destinations.VENTA_SCREEN,
        Destinations.FILTROS_SCREEN,
        Destinations.COCHE_SCREEN,
        Destinations.COMPRA_SCREEN,
        Destinations.COMUNIDAD_SCREEN,
        Destinations.AJUSTES_SCREEN,
        Destinations.RECUPERAR_CONTRASEÑA_SCREEN,
        Destinations.EDITAR_COCHE_SCREEN,
        Destinations.AÑADIR_COCHE_SCREEN,
        Destinations.COMUNIDAD_ADMIN_SCREEN,
        Destinations.AJUSTES_ADMIN_SCREN
    )

    LaunchedEffect(currentRoute) {
        showFAB.value = currentRoute !in hideFABDestinations
    }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0.dp),
        topBar = {
            val bottomBarContent = getTopBarForRoute(currentRoute, navController)
            bottomBarContent?.invoke()
        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavGraph(navController)
            }
        },
        bottomBar = {
            val bottomBarContent = getBottomBarForRoute(currentRoute, navController)
            bottomBarContent?.invoke()
        }
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewApp() {
}