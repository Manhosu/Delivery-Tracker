package com.example.deliverytracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliverytracker.models.Motoboy
import com.example.deliverytracker.services.AuthService
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    // Função de cadastro chamada dentro da coroutine
    fun registerUser(email: String, password: String, name: String, callback: (Motoboy?) -> Unit) {
        viewModelScope.launch {
            val motoboy = AuthService.registerUser(email, password, name)
            callback(motoboy)  // Chamando o callback após o cadastro
        }
    }
}
