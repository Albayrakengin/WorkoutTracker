package com.hub.startworkout.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hub.startworkout.StartWorkoutScreen

const val startWorkoutRoute = "start_workout_route"

fun NavController.navigateToStartWorkout(workoutId: String, navOptions: NavOptions? = null) {
    navigate("$startWorkoutRoute/$workoutId", navOptions)
}

fun NavGraphBuilder.startWorkoutScreen(
    onBackClick: () -> Unit,
    onFinishWorkout: () -> Unit
) {
    composable(
        route = "$startWorkoutRoute/{workoutId}"
    ) {
        StartWorkoutScreen(
            onBackClick = onBackClick,
            onFinishWorkout = onFinishWorkout
        )
    }
} 