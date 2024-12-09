package com.example.deliverytracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.deliverytracker.services.AuthService
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RegisterViewModel : ViewModel() {

    // LiveData para expor o estado
    private val _registrationStatus = MutableLiveData<RegistrationStatus>()
    val registrationStatus: LiveData<RegistrationStatus> get() = _registrationStatus

    // Função para registrar o usuário
    suspend fun registerUser(email: String, password: String, name: String) {
        _registrationStatus.value = RegistrationStatus.Loading
        try {
            val user = withContext(Dispatchers.IO) {
                AuthService.registerUser(email, password, name)
            }
            _registrationStatus.value = RegistrationStatus.Success(user)
        } catch (e: Exception) {
            _registrationStatus.value = RegistrationStatus.Error(e.message ?: "Erro desconhecido")
        }
    }

    sealed class RegistrationStatus {
        object Loading : RegistrationStatus()
        data class Success(val user: FirebaseUser?) : RegistrationStatus()
        data class Error(val message: String) : RegistrationStatus()
    }
}
