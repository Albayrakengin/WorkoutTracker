package com.hub.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.hub.common.R
import com.hub.data.model.Workout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddWorkoutScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedWorkoutType by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    
    val workoutTypes = listOf(
        "https://raw.githubusercontent.com/google/material-design-icons/master/png/social/fitness_center/materialicons/48dp/2x/baseline_fitness_center_black_48dp.png" to "Güç Antrenmanı",
        "https://raw.githubusercontent.com/google/material-design-icons/master/png/maps/directions_run/materialicons/48dp/2x/baseline_directions_run_black_48dp.png" to "Kardiyo",
        "https://raw.githubusercontent.com/google/material-design-icons/master/png/social/self_improvement/materialicons/48dp/2x/baseline_self_improvement_black_48dp.png" to "Yoga",
        "https://raw.githubusercontent.com/google/material-design-icons/master/png/device/timer/materialicons/48dp/2x/baseline_timer_black_48dp.png" to "HIIT",
        "https://raw.githubusercontent.com/google/material-design-icons/master/png/social/sports_martial_arts/materialicons/48dp/2x/baseline_sports_martial_arts_black_48dp.png" to "CrossFit",
        "https://raw.githubusercontent.com/google/material-design-icons/master/png/action/accessibility/materialicons/48dp/2x/baseline_accessibility_black_48dp.png" to "Pilates",
        "https://raw.githubusercontent.com/google/material-design-icons/master/png/social/sports/materialicons/48dp/2x/baseline_sports_black_48dp.png" to "Fonksiyonel Antrenman",
        "https://raw.githubusercontent.com/google/material-design-icons/master/png/action/accessibility_new/materialicons/48dp/2x/baseline_accessibility_new_black_48dp.png" to "Esneme",
        "https://raw.githubusercontent.com/google/material-design-icons/master/png/social/person/materialicons/48dp/2x/baseline_person_black_48dp.png" to "Vücut Ağırlığı"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.new_workout)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(stringResource(R.string.workout_name)) },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = workoutTypes.find { it.second == selectedWorkoutType }?.second ?: stringResource(R.string.select_workout_type),
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    workoutTypes.forEach { (imageUrl, name) ->
                        DropdownMenuItem(
                            text = { 
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Surface(
                                        modifier = Modifier
                                            .size(32.dp)
                                            .clip(CircleShape),
                                        color = MaterialTheme.colorScheme.primaryContainer
                                    ) {
                                        AsyncImage(
                                            model = imageUrl,
                                            contentDescription = name,
                                            contentScale = ContentScale.Fit,
                                            modifier = Modifier
                                                .padding(6.dp)
                                                .fillMaxSize()
                                        )
                                    }
                                    Text(name)
                                }
                            },
                            onClick = {
                                selectedWorkoutType = name
                                type = name
                                Log.d("AddWorkoutScreen", "Selected type: $name")
                                expanded = false
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text(stringResource(R.string.description)) },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = {
                    if (name.isNotBlank() && type.isNotBlank()) {
                        val selectedUrl = workoutTypes.find { it.second == type }?.first ?: ""
                        val workout = Workout(
                            name = name,
                            type = type,
                            description = description,
                            imageUrl = selectedUrl
                        )
                        viewModel.createWorkout(workout)
                        onBackClick()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && type.isNotBlank()
            ) {
                Text(stringResource(R.string.save))
            }
        }
    }
} 