package com.hub.composetemplate.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hub.composetemplate.navigation.NavHost
import com.hub.designsystem.theme.WorkoutTheme
import com.hub.feature.auth.navigation.loginRoute
import com.hub.feature.auth.navigation.resetPasswordRoute
import com.hub.feature.auth.navigation.signUpRoute
import com.hub.startworkout.navigation.startWorkoutRoute
import kotlin.reflect.KClass

@Composable
fun TemplateApp(
    appState: AppState,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val currentDestination = appState.currentDestination
    val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val hideBottomBar = currentRoute in listOf(
        loginRoute,
        signUpRoute,
        resetPasswordRoute,
        startWorkoutRoute
    )

    Scaffold(
        containerColor = WorkoutTheme.colors.backgroundColor,
        bottomBar = {
            if (!hideBottomBar) {
                BottomNavigation(
                    modifier = Modifier
                        .navigationBarsPadding()
                        .height(56.dp),
                    backgroundColor = WorkoutTheme.colors.cardBackground,
                    elevation = 8.dp
                ) {
                    appState.topLevelDestinations.forEach { topLevelDestination ->
                        BottomNavigationItem(
                            selected = currentDestination.isRouteInHierarchy(
                                topLevelDestination.route
                            ),
                            icon = {
                                Icon(
                                    imageVector = if (currentDestination.isRouteInHierarchy(
                                            topLevelDestination.route
                                        )
                                    ) {
                                        topLevelDestination.selectedIcon
                                    } else {
                                        topLevelDestination.unSelectedIcon
                                    },
                                    contentDescription = null,
                                    tint = if (currentDestination.isRouteInHierarchy(
                                            topLevelDestination.route
                                        )
                                    ) {
                                        WorkoutTheme.colors.primaryColor
                                    } else {
                                        WorkoutTheme.colors.opacityTextColor
                                    },
                                    modifier = Modifier.size(28.dp)
                                )
                            },
                            onClick = {
                                appState.navigateToTopLevelDestination(topLevelDestination)
                            },
                            selectedContentColor = WorkoutTheme.colors.primaryColor,
                            unselectedContentColor = WorkoutTheme.colors.opacityTextColor
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            appState = appState,
            authViewModel = authViewModel,
            modifier = modifier.padding(padding)
        )
    }
}

private fun NavDestination?.isRouteInHierarchy(route: KClass<*>) =
    this?.hierarchy?.any {
        it.hasRoute(route)
    } ?: false
