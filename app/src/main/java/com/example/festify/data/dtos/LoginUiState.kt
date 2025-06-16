package com.example.festify.data.dtos

data class LoginUiState(
    val email:String = "",
    val password:String = "",

    val emailError:String? = null,
    val passwordError:String? = null,

    val isLoading:Boolean = false,

    val savedData:Boolean = false,

    val messageServer:String = "",
    val isSuccess:Boolean = false,

)
