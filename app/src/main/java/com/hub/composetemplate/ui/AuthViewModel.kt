package com.hub.composetemplate.ui

import androidx.lifecycle.ViewModel
import com.hub.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    val isAuthenticated: Flow<Boolean> = authRepository.isUserAuthenticated
}
