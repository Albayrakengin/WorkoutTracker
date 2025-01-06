package com.hub.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.res.stringResource
import com.hub.common.R
import com.hub.home.components.WorkoutCard
import com.hub.designsystem.theme.WorkoutTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onWorkoutClick: (String) -> Unit,
    onAddWorkoutClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val workouts by viewModel.workouts.collectAsState(initial = emptyList())

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = WorkoutTheme.colors.backgroundColor
    ) {
        Scaffold(
            containerColor = WorkoutTheme.colors.backgroundColor,
            topBar = {
                TopAppBar(
                    title = { 
                        Text(
                            text = stringResource(R.string.workouts),
                            style = MaterialTheme.typography.headlineMedium,
                            color = WorkoutTheme.colors.textColor
                        )
                    },
                    actions = {
                        IconButton(onClick = onAddWorkoutClick) {
                            Icon(
                                Icons.Default.Add, 
                                contentDescription = stringResource(R.string.add),
                                tint = WorkoutTheme.colors.iconTint
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = WorkoutTheme.colors.backgroundColor,
                        titleContentColor = WorkoutTheme.colors.textColor,
                        actionIconContentColor = WorkoutTheme.colors.iconTint
                    )
                )
            }
        ) { padding ->
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(workouts, key = { it.id }) { workout ->
                    WorkoutCard(
                        workout = workout,
                        onClick = { onWorkoutClick(workout.id) }
                    )
                }
            }
        }
    }
}
