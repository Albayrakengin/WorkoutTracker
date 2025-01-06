package com.hub.auth.di

import com.google.firebase.auth.FirebaseAuth
import com.hub.auth.AuthRepository
import com.hub.auth.DefaultAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth): AuthRepository {
        return DefaultAuthRepository(auth)
    }
} 