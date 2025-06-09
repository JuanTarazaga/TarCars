package com.example.tarcars.ui.components.TopBot

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tarcars.R
import com.example.tarcars.ui.components.navigation.Destinations
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {

    val scope = rememberCoroutineScope()
    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()

    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(modifier = Modifier.width(50.dp))

                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.W500)) {
                                append("Tar")
                            }
                            withStyle(style = SpanStyle(color = Color(0xFFFFDE59), fontWeight = FontWeight.W500)) {
                                append("Cars")
                            }
                        },
                        textAlign = TextAlign.Center,
                        fontSize = 35.sp,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(
                        onClick = {
                            scope.launch {
                                val uid = auth.currentUser?.uid
                                if (uid != null) {
                                    try {
                                        val doc = firestore.collection("usuarios").document(uid).get().await()
                                        val rol = doc.getString("rol")
                                        if (rol == "admin") {
                                            navController.navigate(Destinations.AJUSTES_ADMIN_SCREN) {
                                                launchSingleTop = true
                                            }
                                        } else {
                                            navController.navigate(Destinations.AJUSTES_SCREEN) {
                                                launchSingleTop = true
                                            }
                                        }
                                    } catch (e: Exception) {
                                        navController.navigate(Destinations.AJUSTES_SCREEN) {
                                            launchSingleTop = true
                                        }
                                    }
                                } else {
                                    navController.navigate(Destinations.AJUSTES_SCREEN) {
                                        launchSingleTop = true
                                    }
                                }
                            }
                        },
                        modifier = Modifier.size(50.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ajustes),
                            contentDescription = "Settings",
                            modifier = Modifier.size(40.dp),
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        },
        modifier = Modifier.height(75.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF020202)
        )
    )
}

@Preview
@Composable
fun PreviewTop() {
    TopBar(navController = rememberNavController())
}