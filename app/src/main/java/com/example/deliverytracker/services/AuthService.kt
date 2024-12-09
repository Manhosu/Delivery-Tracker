package com.example.deliverytracker.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.deliverytracker.models.Motoboy
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.tasks.await

object AuthService {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    // Função de cadastro de motoboy
    suspend fun registerUser(email: String, password: String, name: String): Motoboy? {
        try {
            // Cadastro do usuário no Firebase Auth
            val authResult: AuthResult = auth.createUserWithEmailAndPassword(email, password).await()

            // Obter o UID do usuário registrado
            val userId = authResult.user?.uid ?: return null

            // Criar um objeto Motoboy
            val motoboy = Motoboy(userId, name, email)

            // Armazenar os dados no Firestore
            firestore.collection("motoboys")
                .document(userId)
                .set(motoboy)
                .await()

            return motoboy // Retorna o motoboy criado
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
