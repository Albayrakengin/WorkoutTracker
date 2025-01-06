package com.hub.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.hub.detail.DetailScreen
import kotlinx.serialization.Serializable

const val workoutIdArg = "workoutId"
const val workoutDetailsRoute = "workout_details"

@Serializable
data object DetailRoute

fun NavController.navigateToDetail(workoutId: String, navOptions: NavOptions? = null) =
    navigate("$workoutDetailsRoute/$workoutId", navOptions)

fun NavGraphBuilder.detailScreen(
    onBackClick: () -> Unit,
    onStartWorkout: (String) -> Unit
) {
    composable(
        route = "$workoutDetailsRoute/{$workoutIdArg}",
        arguments = listOf(
            navArgument(workoutIdArg) { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val workoutId = backStackEntry.arguments?.getString(workoutIdArg) ?: return@composable
        DetailScreen(
            workoutId = workoutId,
            onBackClick = onBackClick,
            onStartWorkout = { onStartWorkout(workoutId) }
        )
    }
} 