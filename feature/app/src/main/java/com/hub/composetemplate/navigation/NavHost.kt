package com.hub.composetemplate.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hub.auth.onboarding.OnboardingScreen
import com.hub.composetemplate.ui.AppState
import com.hub.composetemplate.ui.AuthViewModel
import com.hub.detail.navigation.detailScreen
import com.hub.detail.navigation.navigateToDetail
import com.hub.feature.auth.navigation.*
import com.hub.home.navigation.*
import com.hub.profile.navigation.profileScreen
import com.hub.settings.navigation.settingsScreen
import com.hub.startworkout.navigation.navigateToStartWorkout
import com.hub.startworkout.navigation.startWorkoutScreen

const val onboardingRoute = "onboarding_route"

@Composable
fun NavHost(
    appState: AppState,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val navController = appState.navController
    val isAuthenticated by authViewModel.isAuthenticated.collectAsState(initial = false)

    NavHost(
        navController = navController,
        startDestination = if (isAuthenticated) HomeRoute.toString() else loginRoute,
        modifier = modifier
    ) {
        // Auth ekranları
        loginScreen(
            onSignUpClick = {
                navController.navigateToSignUp()
            },
            onForgotPasswordClick = {
                navController.navigateToResetPassword()
            }
        )

        signUpScreen(
            onBackClick = {
                navController.popBackStack()
            },
            onSignUpSuccess = {
                // Kayıt başarılı olduğunda onboarding'e yönlendir
                navController.navigate(onboardingRoute) {
                    popUpTo(loginRoute) { inclusive = true }
                }
            }
        )

        resetPasswordScreen(
            onBackClick = {
                navController.popBackStack()
            },
            onResetSuccess = {
                navController.popBackStack()
            }
        )

        // Onboarding ekranı
        composable(route = onboardingRoute) {
            OnboardingScreen(
                onFinishOnboarding = {
                    // Onboarding bittiğinde ana ekrana yönlendir
                    navController.navigate(HomeRoute.toString()) {
                        popUpTo(onboardingRoute) { inclusive = true }
                    }
                }
            )
        }

        // Ana ekranlar
        homeScreen(
            onWorkoutClick = { workoutId ->
                navController.navigateToDetail(workoutId)
            },
            onAddWorkoutClick = {
                navController.navigateToAddWorkout()
            }
        )
        profileScreen(
            onSignOut = {
                navController.navigate(loginRoute) {
                    popUpTo(0) { inclusive = true }
                }
            }
        )
        settingsScreen()

        // Detay ekranı
        detailScreen(
            onBackClick = {
                navController.popBackStack()
            },
            onStartWorkout = { workoutId ->
                navController.navigateToStartWorkout(workoutId)
            }
        )

        // Workout başlatma ekranı
        startWorkoutScreen(
            onBackClick = {
                navController.popBackStack()
            },
            onFinishWorkout = {
                navController.popBackStack()
            }
        )

        // Yeni workout ekleme ekranı
        addWorkoutScreen(
            onBackClick = {
                navController.popBackStack()
            }
        )
    }
}
