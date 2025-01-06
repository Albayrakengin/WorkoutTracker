package com.hub.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hub.home.AddWorkoutScreen
import com.hub.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

const val workoutDetailsRoute = "workout_details"
const val workoutStartRoute = "workout_start"
const val addWorkoutRoute = "add_workout"

fun NavController.navigateToHome(navOptions: NavOptions? = null) =
    navigate(HomeRoute.toString(), navOptions)

fun NavController.navigateToWorkoutDetails(workoutId: String) =
    navigate("$workoutDetailsRoute/$workoutId")

fun NavController.navigateToWorkoutStart(workoutId: String) =
    navigate("$workoutStartRoute/$workoutId")

fun NavController.navigateToAddWorkout() =
    navigate(addWorkoutRoute)

fun NavGraphBuilder.homeScreen(
    onWorkoutClick: (String) -> Unit,
    onAddWorkoutClick: () -> Unit
) {
    composable(route = HomeRoute.toString()) {
        HomeScreen(
            onWorkoutClick = onWorkoutClick,
            onAddWorkoutClick = onAddWorkoutClick
        )
    }
}

fun NavGraphBuilder.addWorkoutScreen(
    onBackClick: () -> Unit
) {
    composable(route = addWorkoutRoute) {
        AddWorkoutScreen(
            onBackClick = onBackClick
        )
    }
}
