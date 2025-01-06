package com.hub.feature.auth.reset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hub.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ResetPasswordState(
    val email: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

sealed interface ResetPasswordEvent {
    data class EmailChanged(val email: String) : ResetPasswordEvent
    data object ResetClicked : ResetPasswordEvent
    data object ErrorDismissed : ResetPasswordEvent
}

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(ResetPasswordState())
        private set

    fun onEvent(event: ResetPasswordEvent) {
        when (event) {
            is ResetPasswordEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            ResetPasswordEvent.ResetClicked -> {
                resetPassword()
            }
            ResetPasswordEvent.ErrorDismissed -> {
                state = state.copy(error = null)
            }
        }
    }

    private fun resetPassword() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            
            val result = authRepository.resetPassword(state.email)
            
            state = when {
                result.isSuccess -> state.copy(
                    isLoading = false,
                    isSuccess = true
                )
                result.isFailure -> state.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message ?: "Bilinmeyen bir hata oluştu"
                )
                else -> state.copy(isLoading = false)
            }
        }
    }
} 