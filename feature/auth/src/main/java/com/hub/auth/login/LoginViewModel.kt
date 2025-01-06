package com.hub.feature.auth.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hub.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent
    data class PasswordChanged(val password: String) : LoginEvent
    data object LoginClicked : LoginEvent
    data object ErrorDismissed : LoginEvent
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(LoginState())
        private set

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is LoginEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            LoginEvent.LoginClicked -> {
                login()
            }
            LoginEvent.ErrorDismissed -> {
                state = state.copy(error = null)
            }

            else -> {}
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            
            val result = authRepository.signIn(state.email, state.password)
            
            state = when {
                result.isSuccess -> state.copy(isLoading = false)
                result.isFailure -> state.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message ?: "Bilinmeyen bir hata oluştu"
                )
                else -> state.copy(isLoading = false)
            }
        }
    }
} 