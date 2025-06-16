package com.example.festify.ui.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.festify.data.dtos.RegisterUiState
import com.example.festify.data.dtos.RequestRegisterDto
import com.example.festify.data.interfaces.AuthRepositoryI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthRegisterViewModel @Inject constructor(
    private val authRepository: AuthRepositoryI
):ViewModel() {

    //attributes
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState = _uiState.asStateFlow()

    fun onUsernameChange(newUsername: String) {
        if (_uiState.value.username != newUsername) {
            _uiState.update { current ->
                current.copy(
                    username = newUsername,
                    usernameError = null
                )
            }
        }
    }

    fun onEmailChange(newEmail: String) {
        if (_uiState.value.email != newEmail) {
            _uiState.update { current ->
                current.copy(
                    email = newEmail,
                    emailError = null
                )
            }
        }
    }

    fun onPasswordChange(newPassword: String) {
        if (_uiState.value.password != newPassword) {
            _uiState.update { current ->
                current.copy(
                    password = newPassword,
                    passwordError = null
                )
            }
        }
    }


    fun onRegister(){
        //propiedades de validacion
        val emailError = validateEmail(_uiState.value.email)
        val passwordError = validatePassword(_uiState.value.password)
        val usernameError = validateUsername(_uiState.value.username)

        //validacion de errores
        if (!emailError.isNullOrEmpty() || !passwordError.isNullOrEmpty() || !usernameError.isNullOrEmpty()){
            _uiState.update {
                it.copy(
                    emailError = emailError.orEmpty(),
                    passwordError = passwordError.orEmpty(),
                    usernameError = usernameError.orEmpty()
                )
            }
            println("error")
            return
        }

        //si son validos se hace el registro
        val request = RequestRegisterDto(
            user = _uiState.value.username,
            email = _uiState.value.email,
            password = _uiState.value.password
        )

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }

            val isRegister = authRepository.register(request)
            if (!isRegister){
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = false,
                        messageServer = "Error en el servidor"

                    )

                }
                return@launch
            }
            _uiState.update {
                it.copy(
                    isSuccess = true,
                    isLoading = false
                )
            }

        }
        return
    }

    private fun validateEmail(email: String): String? {
        return if (email.isBlank()) "El email es obligatorio"
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Email inválido"
        else null
    }

    private fun validatePassword(password: String): String? {
        return if (password.length < 6) "Debe tener al menos 6 caracteres" else null
    }
    private fun validateUsername(username: String): String? {
        return if (username.length < 6) "Debe tener al menos 6 caracteres" else null
    }

}