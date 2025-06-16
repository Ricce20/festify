package com.example.festify.data.interfaces

import com.example.festify.data.dtos.RequestLoginDto
import com.example.festify.data.dtos.RequestRegisterDto

interface AuthRepositoryI {
    suspend fun login(data:RequestLoginDto):Boolean
    suspend fun register(data:RequestRegisterDto):Boolean
}