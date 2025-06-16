package com.example.festify

import com.example.festify.data.interfaces.AuthRepositoryI
import com.example.festify.data.repository.AuthRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class Implements {

    @Binds
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImp
    ):AuthRepositoryI
}