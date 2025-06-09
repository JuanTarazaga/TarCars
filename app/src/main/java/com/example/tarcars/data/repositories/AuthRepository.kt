package com.example.tarcars.data.repositories

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {
    suspend fun registerUser(nombre: String, correo: String, telefono: String, password: String): Result<Unit> {
        return try {
            val result = auth.createUserWithEmailAndPassword(correo, password).await()
            val uid = result.user?.uid ?: return Result.failure(Exception("No UID"))

            val userData = mapOf(
                "nombre" to nombre,
                "correo" to correo,
                "telefono" to telefono,
                "rol" to "usuario",
                "baneado" to false
            )

            firestore.collection("usuarios").document(uid).set(userData).await()

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginUser(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserRole(): Result<String> {
        return try {
            val uid = auth.currentUser?.uid
                ?: return Result.failure(Exception("Usuario no autenticado"))

            val snapshot = firestore.collection("usuarios").document(uid).get().await()
            val rol = snapshot.getString("rol")

            if (rol != null) {
                Result.success(rol)
            } else {
                Result.failure(Exception("Rol no encontrado para el usuario"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}