package com.hub.data.repository

import com.hub.network.firebase.FirebaseService
import javax.inject.Inject

class LanguageRepository @Inject constructor(
    private val firebaseService: FirebaseService
) {
    suspend fun saveUserLanguage(userId: String, languageCode: String) {
        firebaseService.saveUserLanguage(userId, languageCode)
    }

    suspend fun getUserLanguage(userId: String): String? {
        return firebaseService.getUserLanguage(userId)
    }
} 