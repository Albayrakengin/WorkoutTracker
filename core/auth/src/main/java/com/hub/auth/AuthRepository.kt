package com.hub.auth

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val isUserAuthenticated: Flow<Boolean>
    val currentUserId: String?
    
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun signUp(email: String, password: String): Result<Unit>
    suspend fun signOut(): Result<Unit>
    suspend fun resetPassword(email: String): Result<Unit>
} 