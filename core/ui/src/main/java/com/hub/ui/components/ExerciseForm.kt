package com.hub.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class ExerciseFormData(
    val name: String = "",
    val defaultSets: Int = 3,
    val defaultReps: Int = 12,
    val defaultWeight: Int = 0,
    val notes: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseForm(
    onSave: (ExerciseFormData) -> Unit,
    modifier: Modifier = Modifier
) {
    var formData by remember { mutableStateOf(ExerciseFormData()) }
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Exercise Name
        OutlinedTextField(
            value = formData.name,
            onValueChange = { formData = formData.copy(name = it) },
            label = { Text("Exercise Name") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Default Sets
        OutlinedTextField(
            value = formData.defaultSets.toString(),
            onValueChange = { value ->
                value.toIntOrNull()?.let {
                    formData = formData.copy(defaultSets = it)
                }
            },
            label = { Text("Default Sets") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Default Reps
        OutlinedTextField(
            value = formData.defaultReps.toString(),
            onValueChange = { value ->
                value.toIntOrNull()?.let {
                    formData = formData.copy(defaultReps = it)
                }
            },
            label = { Text("Default Reps") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Default Weight
        OutlinedTextField(
            value = formData.defaultWeight.toString(),
            onValueChange = { value ->
                value.toIntOrNull()?.let {
                    formData = formData.copy(defaultWeight = it)
                }
            },
            label = { Text("Default Weight (kg)") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Notes
        OutlinedTextField(
            value = formData.notes,
            onValueChange = { formData = formData.copy(notes = it) },
            label = { Text("Notes") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Save Button
        Button(
            onClick = { onSave(formData) },
            modifier = Modifier.fillMaxWidth(),
            enabled = formData.name.isNotBlank()
        ) {
            Text("Save Exercise")
        }
    }
} 