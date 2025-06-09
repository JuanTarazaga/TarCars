package com.example.tarcars.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tarcars.ui.screens.Admin.AdminScreen
import com.example.tarcars.ui.screens.Admin.AjustesAdmin.AjustesAdminScreen
import com.example.tarcars.ui.screens.Admin.AñadirCoches.AñadirCocheScreen
import com.example.tarcars.ui.screens.Admin.ComunidadAdmin.ComunidadAdminScreen
import com.example.tarcars.ui.screens.Admin.EditarCoches.EditarCocheScreen
import com.example.tarcars.ui.screens.Ajustes.AjustesScreen
import com.example.tarcars.ui.screens.Coche.CocheScreen
import com.example.tarcars.ui.screens.Coche.CocheViewModel
import com.example.tarcars.ui.screens.Compra.CompraScreen
import com.example.tarcars.ui.screens.Comunidad.ComunidadScreen
import com.example.tarcars.ui.screens.Filtros.FiltrosScreen
import com.example.tarcars.ui.screens.Login.LoginScreen
import com.example.tarcars.ui.screens.RecuperarContraseña.RecuperarContraseñaScreen
import com.example.tarcars.ui.screens.Registro.RegistroScreen
import com.example.tarcars.ui.screens.Splash.SplashScreen
import com.example.tarcars.ui.screens.Venta.VentaScreen

@Composable
fun NavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Destinations.SPLASH_SCREEN
    ){
        composable(Destinations.SPLASH_SCREEN) {
            SplashScreen(navController = navController)
        }

        composable(Destinations.LOGIN_SCREEN) {
            LoginScreen(navController = navController)
        }

        composable(Destinations.REGISTRO_SCREEN) {
            RegistroScreen(navController = navController)
        }

        composable(Destinations.VENTA_SCREEN) { navBackStackEntry ->
            VentaScreen(
                navController = navController,
                navBackStackEntry = navBackStackEntry
            )
        }

        composable(Destinations.FILTROS_SCREEN) {
            FiltrosScreen(navController = navController)
        }

        composable(
            route = "${Destinations.COCHE_SCREEN}/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val viewModel: CocheViewModel = hiltViewModel()

            // Cargar el coche cuando se abre la pantalla o cambia el ID
            LaunchedEffect(key1 = id) {
                if (id.isNotEmpty()) {
                    viewModel.getCoche(id)
                }
            }

            CocheScreen(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(Destinations.COMPRA_SCREEN) {
            CompraScreen(navController = navController)
        }

        composable(Destinations.COMUNIDAD_SCREEN) {
            ComunidadScreen(navController = navController)
        }

        composable(Destinations.AJUSTES_SCREEN) {
            AjustesScreen(navController = navController)
        }

        composable(Destinations.RECUPERAR_CONTRASEÑA_SCREEN) {
            RecuperarContraseñaScreen(navController = navController)
        }

        composable(Destinations.ADMIN_SCREEN) {
            AdminScreen(navController = navController)
        }

        composable("${Destinations.EDITAR_COCHE_SCREEN}/{cocheId}") { backStackEntry ->
            val cocheId = backStackEntry.arguments?.getString("cocheId") ?: ""
            EditarCocheScreen(navController, cocheId)
        }

        composable(Destinations.AÑADIR_COCHE_SCREEN) {
            AñadirCocheScreen(navController = navController)
        }

        composable(Destinations.COMUNIDAD_ADMIN_SCREEN) {
            ComunidadAdminScreen(navController = navController)
        }

        composable(Destinations.AJUSTES_ADMIN_SCREN) {
            AjustesAdminScreen(navController = navController)
        }
    }
}