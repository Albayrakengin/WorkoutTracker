package com.hub.composetemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.hub.common.language.LanguageManager
import com.hub.composetemplate.ui.AppState
import com.hub.composetemplate.ui.AuthViewModel
import com.hub.composetemplate.ui.TemplateApp
import com.hub.composetemplate.ui.rememberAppState
import com.hub.designsystem.theme.WorkoutTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var languageManager: LanguageManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            languageManager.loadUserLanguage(this@MainActivity)
        }

        setContent {
            WorkoutTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = WorkoutTheme.colors.backgroundColor
                ) {
                    val appState: AppState = rememberAppState()
                    TemplateApp(
                        appState = appState,
                        authViewModel = authViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            try {
                languageManager.loadUserLanguage(this@MainActivity)
            } catch (e: Exception) {
                // Hata durumunu handle et
            }
        }
    }
}
