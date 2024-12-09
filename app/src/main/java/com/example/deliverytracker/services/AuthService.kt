package com.example.deliverytracker.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object AuthService {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    data class User(
        val id: String = "",
        val name: String = "",
        val email: String = "",
        val role: String = "motoboy" // Default role
    )

    // Função para cadastrar usuários
    suspend fun registerUser(email: String, password: String, name: String): FirebaseUser? {
        try {
            // Criar o usuário no Firebase Auth
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val user = result.user

            // Salvar detalhes do usuário no Firestore
            user?.let {
                val userData = User(
                    id = it.uid,
                    name = name,
                    email = email
                )
                firestore.collection("users").document(it.uid).set(userData).await()
            }

            return user
        } catch (e: Exception) {
            e.printStackTrace()
            throw e // Pode ser tratado na UI
        }
    }
}
