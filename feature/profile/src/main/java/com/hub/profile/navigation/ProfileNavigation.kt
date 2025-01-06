package com.hub.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hub.profile.ProfileScreen
import kotlinx.serialization.Serializable

@Serializable
data object ProfileRoute

const val profileRoute = "profile_route"

fun NavController.navigateToProfile(navOptions: NavOptions? = null) {
    navigate(profileRoute, navOptions)
}

fun NavGraphBuilder.profileScreen(
    onSignOut: () -> Unit
) {
    composable(route = profileRoute) {
        ProfileScreen(onSignOut = onSignOut)
    }
} 