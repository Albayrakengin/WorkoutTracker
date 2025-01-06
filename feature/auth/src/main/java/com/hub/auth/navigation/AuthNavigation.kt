package com.hub.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

import com.hub.feature.auth.login.LoginScreen
import com.hub.feature.auth.reset.ResetPasswordScreen
import com.hub.feature.auth.signup.SignUpScreen

import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

@Serializable
data object SignUpRoute

@Serializable
data object ResetPasswordRoute

const val loginRoute = "login_route"
const val signUpRoute = "sign_up_route"
const val resetPasswordRoute = "reset_password_route"

fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    navigate(loginRoute, navOptions)
}

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    navigate(signUpRoute, navOptions)
}

fun NavController.navigateToResetPassword(navOptions: NavOptions? = null) {
    navigate(resetPasswordRoute, navOptions)
}

fun NavGraphBuilder.loginScreen(
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    composable(route = loginRoute) {
        LoginScreen(
            onSignUpClick = onSignUpClick,
            onForgotPasswordClick = onForgotPasswordClick
        )
    }
}

fun NavGraphBuilder.signUpScreen(
    onBackClick: () -> Unit,
    onSignUpSuccess: () -> Unit
) {
    composable(route = signUpRoute) {
        SignUpScreen(
            onBackClick = onBackClick,
            onSignUpSuccess = onSignUpSuccess
        )
    }
}

fun NavGraphBuilder.resetPasswordScreen(
    onBackClick: () -> Unit,
    onResetSuccess: () -> Unit
) {
    composable(route = resetPasswordRoute) {
        ResetPasswordScreen(
            onBackClick = onBackClick,
            onResetSuccess = onResetSuccess
        )
    }
} 