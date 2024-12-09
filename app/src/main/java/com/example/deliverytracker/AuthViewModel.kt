package com.example.deliverytracker.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.deliverytracker.viewmodel.AuthViewModel

class LoginActivity<AuthViewModel> : AppCompatActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            authViewModel.loginUser(email, password, {
                // Sucesso
                Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
                // Redirecionar para a próxima tela ou salvar a sessão do usuário
            }, {
                // Falha
                Toast.makeText(this, "Erro: $it", Toast.LENGTH_SHORT).show()
            })
        }
    }
}
