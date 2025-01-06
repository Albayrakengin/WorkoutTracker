package com.hub.feature.auth.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hub.auth.AuthRepository
import com.hub.data.usecase.UserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SignUpState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

sealed interface SignUpEvent {
    data class EmailChanged(val email: String) : SignUpEvent
    data class PasswordChanged(val password: String) : SignUpEvent
    data class ConfirmPasswordChanged(val confirmPassword: String) : SignUpEvent
    data object SignUpClicked : SignUpEvent
    data object ErrorDismissed : SignUpEvent
}

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userDataUseCase: UserDataUseCase
) : ViewModel() {

    var state by mutableStateOf(SignUpState())
        private set

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is SignUpEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is SignUpEvent.ConfirmPasswordChanged -> {
                state = state.copy(confirmPassword = event.confirmPassword)
            }
            SignUpEvent.SignUpClicked -> {
                signUp()
            }
            SignUpEvent.ErrorDismissed -> {
                state = state.copy(error = null)
            }
        }
    }

    private fun signUp() {
        if (state.password != state.confirmPassword) {
            state = state.copy(error = "Şifreler eşleşmiyor")
            return
        }

        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            
            try {
                val result = authRepository.signUp(state.email, state.password)
                
                if (result.isSuccess) {
                    // Kullanıcı başarıyla kaydedildikten sonra Firestore'da profil oluştur
                    userDataUseCase.createInitialUserProfile(state.email)
                    state = state.copy(isLoading = false, isSuccess = true)
                } else {
                    state = state.copy(
                        isLoading = false,
                        error = result.exceptionOrNull()?.message ?: "Bilinmeyen bir hata oluştu"
                    )
                }
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    error = e.message ?: "Bilinmeyen bir hata oluştu"
                )
            }
        }
    }
} 