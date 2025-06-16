package com.example.festify.ui.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.festify.data.dtos.LoginUiState
import com.example.festify.data.dtos.RequestLoginDto
import com.example.festify.data.interfaces.AuthRepositoryI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthLoginViewModel @Inject constructor(
    private val authRepository: AuthRepositoryI
) : ViewModel() {
    //atributos de UI
    private val _uiLoginState = MutableStateFlow(LoginUiState())
    val uiLoginState = _uiLoginState.asStateFlow()

    fun onEmailChange(newEmail: String) {
        if (_uiLoginState.value.email != newEmail) {
            _uiLoginState.update { current ->
                current.copy(
                    email = newEmail,
                    emailError = null
                )
            }
        }
    }

    fun onPasswordChange(newPassword: String) {
        if (_uiLoginState.value.password != newPassword) {
            _uiLoginState.update { current ->
                current.copy(
                    password = newPassword,
                    passwordError = null
                )
            }
        }

    }

    fun onSaveDataChange(newSaveData: Boolean) {
        if (_uiLoginState.value.savedData != newSaveData) {
            _uiLoginState.update { current ->
                current.copy(
                    savedData = newSaveData
                )
            }
        }
    }

    fun onClickCip(){
        if (_uiLoginState.value.savedData){
            _uiLoginState.update {
                current ->
                current.copy(
                    savedData = false
                )
            }
            return
        }
        _uiLoginState.update {
            current ->
            current.copy(
                savedData = true
            )
        }
        return

    }


    fun onLogin(){
        val emailError = validateEmail(_uiLoginState.value.email)
        val passwordError = validatePassword(_uiLoginState.value.password)

        if (!emailError.isNullOrEmpty() || !passwordError.isNullOrEmpty()) {
            _uiLoginState.update {
                it.copy(
                    emailError = emailError.orEmpty(),
                    passwordError = passwordError.orEmpty()
                )
            }
            return
        }

        //datos
        val request = RequestLoginDto(
            email = _uiLoginState.value.email,
            password = _uiLoginState.value.password
        )

        viewModelScope.launch {
            _uiLoginState.update {
                current -> current.copy(
                    isLoading = true
                )
            }

            val isLogin = authRepository.login(request)
            if (!isLogin){
                _uiLoginState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = false,
                    )
                }
                return@launch
            }
            _uiLoginState.update {
                it.copy(
                    isSuccess = true,
                    isLoading = false
                )
            }

        }


    }

    private fun validateEmail(email: String): String? {
        return if (email.isBlank()) "El email es obligatorio"
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Email inválido"
        else null
    }

    private fun validatePassword(password: String): String? {
        return if (password.length < 6) "Debe tener al menos 6 caracteres" else null
    }
}