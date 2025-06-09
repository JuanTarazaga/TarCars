package com.example.tarcars.ui.components.TopBot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tarcars.R
import com.example.tarcars.ui.components.navigation.Destinations

@Composable
fun BottomBar(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(Color(0xFF020000))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { navController.navigate(Destinations.COMPRA_SCREEN){
                        launchSingleTop = true
                    }
                              },
                    modifier = Modifier.size(50.dp),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.compra),
                        contentDescription = "compra",
                        modifier = Modifier.size(35.dp),
                        tint = Color.Unspecified
                    )
                }
            }
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { navController.navigate(Destinations.VENTA_SCREEN){
                        launchSingleTop = true
                    }
                              },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.coche),
                        contentDescription = "venta",
                        modifier = Modifier.size(40.dp),
                        tint = Color.Unspecified
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    onClick = { navController.navigate(Destinations.COMUNIDAD_SCREEN){
                        launchSingleTop = true
                    }
                              },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.grupo),
                        contentDescription = "comunidad",
                        modifier = Modifier.size(35.dp),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewBot() {
    BottomBar(navController = rememberNavController())
}