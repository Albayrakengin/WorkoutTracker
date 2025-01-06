package com.hub.startworkout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.activity.compose.BackHandler
import androidx.compose.ui.res.stringResource
import com.hub.common.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartWorkoutScreen(
    onBackClick: () -> Unit,
    onFinishWorkout: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StartWorkoutViewModel = hiltViewModel()
) {
    val workout by viewModel.workout.collectAsState()
    val exercises by viewModel.exercises.collectAsState()
    val state = viewModel.state
    val currentExercise = viewModel.currentExercise

    BackHandler {
        viewModel.onEvent(StartWorkoutEvent.ShowExitDialog)
    }

    if (state.showExitDialog) {
        AlertDialog(
            onDismissRequest = { viewModel.onEvent(StartWorkoutEvent.HideExitDialog) },
            title = { Text(stringResource(R.string.end_workout)) },
            text = { Text(stringResource(R.string.end_workout_confirm)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.onEvent(StartWorkoutEvent.FinishWorkout)
                        onFinishWorkout()
                    }
                ) {
                    Text(stringResource(R.string.yes))
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.onEvent(StartWorkoutEvent.HideExitDialog) }) {
                    Text(stringResource(R.string.no))
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(currentExercise?.name ?: "") },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onEvent(StartWorkoutEvent.ShowExitDialog) }) {
                        Icon(Icons.Default.Close, contentDescription = stringResource(R.string.close))
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Egzersiz Bilgileri
            if (!currentExercise?.description.isNullOrEmpty()) {
                Text(
                    text = currentExercise?.description ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Set, Tekrar ve Ağırlık Ayarları
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.weight(1f)
            ) {
                WorkoutMetricCard(
                    label = "Set",
                    value = state.currentSets,
                    onValueChange = { viewModel.onEvent(StartWorkoutEvent.UpdateSets(it)) },
                    onIncrease = { viewModel.onEvent(StartWorkoutEvent.UpdateSets(state.currentSets + 1)) },
                    onDecrease = { if (state.currentSets > 0) viewModel.onEvent(StartWorkoutEvent.UpdateSets(state.currentSets - 1)) }
                )

                WorkoutMetricCard(
                    label = "Rep",
                    value = state.currentReps,
                    onValueChange = { viewModel.onEvent(StartWorkoutEvent.UpdateReps(it)) },
                    onIncrease = { viewModel.onEvent(StartWorkoutEvent.UpdateReps(state.currentReps + 1)) },
                    onDecrease = { if (state.currentReps > 0) viewModel.onEvent(StartWorkoutEvent.UpdateReps(state.currentReps - 1)) }
                )

                WorkoutMetricCard(
                    label = "Weight",
                    value = state.currentWeight.toInt(),
                    unit = "kg",
                    onValueChange = { viewModel.onEvent(StartWorkoutEvent.UpdateWeight(it.toFloat())) },
                    onIncrease = { viewModel.onEvent(StartWorkoutEvent.UpdateWeight(state.currentWeight + 5f)) },
                    onDecrease = { if (state.currentWeight >= 5) viewModel.onEvent(StartWorkoutEvent.UpdateWeight(state.currentWeight - 5f)) }
                )
            }

            // Önceki Antrenman Bilgileri
            state.previousExerciseHistory?.let { history ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Text(
                        text = "Previous Exercise",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "s: ${history.sets}  r: ${history.reps}  w: ${history.weight}kg",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            // Navigasyon Butonları
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { viewModel.onEvent(StartWorkoutEvent.PreviousExercise) },
                    enabled = viewModel.hasPreviousExercise,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.previous))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(stringResource(R.string.previous))
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        if (viewModel.hasNextExercise) {
                            viewModel.onEvent(StartWorkoutEvent.NextExercise)
                        } else {
                            viewModel.onEvent(StartWorkoutEvent.ShowExitDialog)
                        }
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Text(if (viewModel.hasNextExercise) stringResource(R.string.next) else stringResource(R.string.finish))
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.ArrowForward, contentDescription = stringResource(R.string.next))
                }
            }
        }
    }
}

@Composable
fun WorkoutMetricCard(
    label: String,
    value: Int,
    unit: String = "",
    onValueChange: (Int) -> Unit,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.width(IntrinsicSize.Min)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { onDecrease() },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = stringResource(R.string.previous))
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                var isEditing by remember { mutableStateOf(false) }
                var textValue by remember(value) { mutableStateOf(value.toString()) }

                if (isEditing) {
                    OutlinedTextField(
                        value = textValue,
                        onValueChange = { newValue ->
                            if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                                textValue = newValue
                                newValue.toIntOrNull()?.let { onValueChange(it) }
                            }
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        modifier = Modifier.width(100.dp),
                        textStyle = MaterialTheme.typography.headlineMedium.copy(
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    )
                } else {
                    Text(
                        text = value.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { isEditing = true }
                    )
                }

                if (unit.isNotEmpty()) {
                    Text(
                        text = unit,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Editing'den çıkış ve değer güncelleme
                LaunchedEffect(isEditing) {
                    if (!isEditing && textValue.isNotEmpty()) {
                        val newValue = textValue.toIntOrNull() ?: value
                        if (newValue != value) {
                            onValueChange(newValue)
                        }
                        textValue = value.toString()
                    }
                }

                DisposableEffect(Unit) {
                    onDispose {
                        if (isEditing) {
                            isEditing = false
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { onIncrease() },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.KeyboardArrowUp, contentDescription = stringResource(R.string.next))
            }
        }
    }
} 