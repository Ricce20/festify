package com.example.festify.data.dtos

import com.example.festify.data.model.User

data class ResponseLoginDto(
    val user: User,
    val jwt:String?,
    val refreshToken:String?
)
