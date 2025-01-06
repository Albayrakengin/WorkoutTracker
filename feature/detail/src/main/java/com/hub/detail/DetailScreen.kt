package com.hub.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hub.common.R
import com.hub.detail.components.ExerciseForm
import com.hub.data.model.Exercise
import com.hub.ui.ExerciseDetailCard
import com.hub.designsystem.theme.WorkoutTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
    workoutId: String,
    onBackClick: () -> Unit,
    onStartWorkout: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val workout by viewModel.workout.collectAsState()
    val exercises by viewModel.exercises.collectAsState(initial = emptyList())
    var showAddExerciseSheet by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = WorkoutTheme.colors.backgroundColor,
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = workout?.name ?: "",
                        color = WorkoutTheme.colors.textColor
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack, 
                            contentDescription = stringResource(R.string.back),
                            tint = WorkoutTheme.colors.iconTint
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Edit workout */ }) {
                        Icon(
                            Icons.Default.Edit, 
                            contentDescription = stringResource(R.string.edit),
                            tint = WorkoutTheme.colors.iconTint
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = WorkoutTheme.colors.backgroundColor,
                    titleContentColor = WorkoutTheme.colors.textColor,
                    navigationIconContentColor = WorkoutTheme.colors.iconTint,
                    actionIconContentColor = WorkoutTheme.colors.iconTint
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddExerciseSheet = true },
                containerColor = WorkoutTheme.colors.primaryColor,
                contentColor = WorkoutTheme.colors.textColorReverse
            ) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add))
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                Spacer(modifier = Modifier.height(32.dp))
                if (!workout?.description.isNullOrEmpty() || !workout?.notes.isNullOrEmpty()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = WorkoutTheme.colors.cardBackground
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            if (!workout?.description.isNullOrEmpty()) {
                                Text(
                                    text = workout?.description ?: "",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = WorkoutTheme.colors.textColor
                                )
                            }
                            if (!workout?.notes.isNullOrEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = workout?.notes ?: "",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = WorkoutTheme.colors.opacityTextColor
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }

            stickyHeader {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = WorkoutTheme.colors.backgroundColor
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Button(
                            onClick = onStartWorkout,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = WorkoutTheme.colors.primaryColor,
                                contentColor = WorkoutTheme.colors.textColorReverse
                            )
                        ) {
                            Text(stringResource(R.string.start_workout))
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = stringResource(R.string.exercise_name),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = WorkoutTheme.colors.textColor
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }

            items(exercises, key = { it.id }) { exercise ->
                ExerciseDetailCard(
                    exerciseName = exercise.name,
                    lastWeight = exercise.weight.toInt(),
                    sessionWeights = exercise.history.takeLast(5).map { it.weight.toInt() },
                    sets = exercise.sets,
                    reps = exercise.reps,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        if (showAddExerciseSheet) {
            ModalBottomSheet(
                onDismissRequest = { showAddExerciseSheet = false },
                containerColor = WorkoutTheme.colors.bottomSheetBackground
            ) {
                ExerciseForm(
                    onSave = { name, sets, reps, weight ->
                        val exercise = Exercise(
                            name = name,
                            workoutId = workoutId,
                            sets = sets,
                            reps = reps,
                            weight = weight.toFloat()
                        )
                        viewModel.createExercise(exercise)
                        showAddExerciseSheet = false
                    },
                    onDismiss = { showAddExerciseSheet = false }
                )
            }
        }
    }
} 