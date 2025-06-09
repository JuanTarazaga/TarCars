package com.example.tarcars.ui.components.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.tarcars.ui.components.TopBot.BottomBar
import com.example.tarcars.ui.components.TopBot.TopBar

object Destinations {
    const val SPLASH_SCREEN = "splash-screen"
    const val VENTA_SCREEN = "venta-screen"
    const val COMPRA_SCREEN = "compra-screen"
    const val COMUNIDAD_SCREEN = "comunidad-screen"
    const val LOGIN_SCREEN = "login-screen"
    const val REGISTRO_SCREEN = "registro-screen"
    const val AJUSTES_SCREEN = "ajustes-screen"
    const val FILTROS_SCREEN = "filtros-screen"
    const val COCHE_SCREEN = "coche-screen"
    const val RECUPERAR_CONTRASEÑA_SCREEN = "recuperar-contraseña-screen"
    const val ADMIN_SCREEN = "admin-screen"
    const val EDITAR_COCHE_SCREEN = "editar-coche-screen"
    const val AÑADIR_COCHE_SCREEN = "añadir-coche-screen"
    const val COMUNIDAD_ADMIN_SCREEN = "comunidad-admin-sceen"
    const val AJUSTES_ADMIN_SCREN = "ajustes-admin-screen"


    fun getBottomBarForRoute(route: String?,navController: NavController): @Composable (() -> Unit)? {
        return when (route) {
            Destinations.COMPRA_SCREEN -> {
                { BottomBar(navController) }
            }

            Destinations.COMUNIDAD_SCREEN -> {
                { BottomBar(navController) }
            }

            Destinations.FILTROS_SCREEN -> {
                { BottomBar(navController) }
            }

            Destinations.VENTA_SCREEN -> {
                { BottomBar(navController) }
            }

            Destinations.COCHE_SCREEN -> {
                { BottomBar(navController) }
            }

            else -> null
        }
    }

    fun getTopBarForRoute(route: String?, navController: NavController): @Composable (() -> Unit)? {
        return when (route) {
            Destinations.COMPRA_SCREEN -> {
                { TopBar(navController) }
            }

            Destinations.COMUNIDAD_SCREEN -> {
                { TopBar(navController) }
            }

            Destinations.FILTROS_SCREEN -> {
                { TopBar(navController) }
            }

            Destinations.AJUSTES_SCREEN -> {
                { TopBar(navController) }
            }

            Destinations.COCHE_SCREEN -> {
                { TopBar(navController) }
            }

            Destinations.VENTA_SCREEN-> {
                { TopBar(navController) }
            }

            Destinations.ADMIN_SCREEN-> {
                { TopBar(navController)}
            }

            Destinations.EDITAR_COCHE_SCREEN-> {
                { TopBar(navController)}
            }

            Destinations.AÑADIR_COCHE_SCREEN-> {
                { TopBar(navController)}
            }

            Destinations.COMUNIDAD_ADMIN_SCREEN-> {
                { TopBar(navController)}
            }

            Destinations.AJUSTES_ADMIN_SCREN -> {
                { TopBar(navController) }
            }
            else -> null
        }
    }
}