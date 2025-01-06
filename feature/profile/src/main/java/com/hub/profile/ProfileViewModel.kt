package com.hub.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hub.auth.AuthRepository
import com.hub.data.model.User
import com.hub.data.usecase.UserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileState(
    val user: User? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isEditMode: Boolean = false,
    val isSignedOut: Boolean = false
)

sealed interface ProfileEvent {
    data class UpdateDisplayName(val name: String) : ProfileEvent
    data object ToggleEditMode : ProfileEvent
    data object SaveProfile : ProfileEvent
    data object ErrorDismissed : ProfileEvent
    data object SignOut : ProfileEvent
}

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userDataUseCase: UserDataUseCase,
    private val authRepository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                userDataUseCase.getUserFlow().collectLatest { user ->
                    state = state.copy(
                        user = user,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    error = e.message ?: "Profil yüklenirken bir hata oluştu"
                )
            }
        }
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.UpdateDisplayName -> {
                state.user?.let { currentUser ->
                    state = state.copy(
                        user = currentUser.copy(displayName = event.name)
                    )
                }
            }
            ProfileEvent.ToggleEditMode -> {
                state = state.copy(isEditMode = !state.isEditMode)
            }
            ProfileEvent.SaveProfile -> {
                saveProfile()
            }
            ProfileEvent.ErrorDismissed -> {
                state = state.copy(error = null)
            }
            ProfileEvent.SignOut -> {
                signOut()
            }
        }
    }

    private fun signOut() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                authRepository.signOut()
                state = state.copy(
                    isLoading = false,
                    isSignedOut = true
                )
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    error = e.message ?: "Çıkış yapılırken bir hata oluştu"
                )
            }
        }
    }

    private fun saveProfile() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                state.user?.let { user ->
                    userDataUseCase.updateUserProfile(
                        user.copy(updatedAt = System.currentTimeMillis())
                    )
                }
                state = state.copy(
                    isLoading = false,
                    isEditMode = false
                )
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    error = e.message ?: "Profil güncellenirken bir hata oluştu"
                )
            }
        }
    }
} 