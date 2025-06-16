package com.example.festify.data.dtos

data class RegisterUiState (
    val username:String = "",
    val email:String = "",
    val password:String = "",
    val usernameError: String? = "",
    val emailError: String? = "",
    val passwordError: String? = "",
    val isLoading:Boolean = false,
    val messageServer:String = "",
    val isSuccess:Boolean = false

)