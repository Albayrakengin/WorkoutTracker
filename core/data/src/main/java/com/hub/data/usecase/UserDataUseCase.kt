package com.hub.data.usecase

import com.hub.data.model.User
import com.hub.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun getCurrentUser(): User? {
        return userRepository.getCurrentUser()
    }

    suspend fun updateUserProfile(user: User) {
        userRepository.updateUserProfile(user)
    }

    fun getUserFlow(): Flow<User?> {
        return userRepository.getUserFlow()
    }

    suspend fun createInitialUserProfile(email: String) {
        val user = User(
            email = email,
            displayName = email.substringBefore("@"),
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis()
        )
        userRepository.updateUserProfile(user)
    }
} 