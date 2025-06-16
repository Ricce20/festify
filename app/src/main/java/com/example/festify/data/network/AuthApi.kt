package com.example.festify.data.network

import com.example.festify.data.dtos.RequestRegisterDto
import com.example.festify.data.dtos.RequestLoginDto
import com.example.festify.data.dtos.ResponseLoginDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/register")
    suspend fun register(@Body data:RequestRegisterDto):Response<ResponseLoginDto>

    @POST("auth/login")
    suspend fun login(@Body data:RequestLoginDto):Response<ResponseLoginDto>
}