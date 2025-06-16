package com.example.festify.data.repository

import com.example.festify.data.dtos.RequestLoginDto
import com.example.festify.data.dtos.RequestRegisterDto
import com.example.festify.data.interfaces.AuthRepositoryI
import com.example.festify.data.network.AuthApi
import javax.inject.Inject

class AuthRepositoryImp @Inject constructor(
    private val authApi: AuthApi
):AuthRepositoryI {

    override suspend fun login(data: RequestLoginDto): Boolean {
        try {
            val response = authApi.login(data)
            if (!response.isSuccessful || response.body() == null){
                return false
            }

            val body = response.body()
            println(body)
            return true
        }catch (e:Exception){
            println("Error en el registro")
            println(e.message)
            return false
        }

    }

    override suspend fun register(data: RequestRegisterDto): Boolean {
        try {
            val response  = authApi.register(data)
            if (!response.isSuccessful || response.body() == null){
                return false
            }
            val body =  response.body()
            println(body)
            return true
        }catch (e:Exception){
            println("Error en el registro")
            println(e.message)
            return false
        }
    }

}