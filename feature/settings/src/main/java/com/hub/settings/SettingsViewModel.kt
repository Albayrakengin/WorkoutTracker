package com.hub.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hub.common.language.LanguageManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val languageManager: LanguageManager
) : ViewModel() {

    val currentLanguage: StateFlow<String> = languageManager.currentLanguage

    fun setLanguage(context: Context, languageCode: String) {
        viewModelScope.launch {
            languageManager.setLanguage(context, languageCode)
        }
    }

    fun loadLanguagePreference(context: Context) {
        viewModelScope.launch {
            try {
                languageManager.loadUserLanguage(context)
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
} 