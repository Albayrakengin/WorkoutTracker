package com.hub.common.language

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.LocaleList
import com.hub.common.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageManager @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    private val _currentLanguage = MutableStateFlow(
        sharedPreferences.getString(PREF_LANGUAGE, Locale.getDefault().language) ?: LANGUAGE_TR
    )
    val currentLanguage: StateFlow<String> = _currentLanguage

    suspend fun setLanguage(context: Context, languageCode: String) {
        withContext(dispatcher) {
            try {
                // Save to SharedPreferences
                sharedPreferences.edit()
                    .putString(PREF_LANGUAGE, languageCode)
                    .apply()

                // Update locale
                updateLocale(context, languageCode)
                _currentLanguage.value = languageCode
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    suspend fun loadUserLanguage(context: Context) {
        withContext(dispatcher) {
            try {
                val languageCode = sharedPreferences.getString(PREF_LANGUAGE, Locale.getDefault().language) 
                    ?: LANGUAGE_TR
                updateLocale(context, languageCode)
                _currentLanguage.value = languageCode
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private fun updateLocale(context: Context, languageCode: String) {
        try {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)

            val config = context.resources.configuration
            val localeList = LocaleList(locale)
            LocaleList.setDefault(localeList)
            config.setLocales(localeList)

            context.createConfigurationContext(config)
            context.resources.updateConfiguration(config, context.resources.displayMetrics)

            // Uygulama genelinde locale'i güncelle
            val appContext = context.applicationContext
            appContext.resources.configuration.setLocales(localeList)
            appContext.resources.updateConfiguration(config, appContext.resources.displayMetrics)
            appContext.createConfigurationContext(config)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        const val LANGUAGE_TR = "tr"
        const val LANGUAGE_EN = "en"
        private const val PREF_LANGUAGE = "pref_language"
        private const val DEFAULT_LANGUAGE = LANGUAGE_TR
    }
} 