package com.hub.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hub.settings.SettingsScreen
import kotlinx.serialization.Serializable

@Serializable
data object SettingsRoute

fun NavController.navigateToSettings(navOptions: NavOptions? = null) =
    navigate(SettingsRoute.toString(), navOptions)

fun NavGraphBuilder.settingsScreen() {
    composable(route = SettingsRoute.toString()) {
        SettingsScreen()
    }
} 