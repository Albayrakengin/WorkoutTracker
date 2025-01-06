package com.hub.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class WorkoutFormData(
    val name: String = "",
    val description: String = "",
    val type: String = "",
    val notes: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutForm(
    onSave: (WorkoutFormData) -> Unit,
    modifier: Modifier = Modifier
) {
    var formData by remember { mutableStateOf(WorkoutFormData()) }
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Workout Name
        OutlinedTextField(
            value = formData.name,
            onValueChange = { formData = formData.copy(name = it) },
            label = { Text("Workout Name") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Workout Type
        OutlinedTextField(
            value = formData.type,
            onValueChange = { formData = formData.copy(type = it) },
            label = { Text("Workout Type (e.g., Back, Chest, Legs)") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Description
        OutlinedTextField(
            value = formData.description,
            onValueChange = { formData = formData.copy(description = it) },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Notes
        OutlinedTextField(
            value = formData.notes,
            onValueChange = { formData = formData.copy(notes = it) },
            label = { Text("Notes") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 2
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Save Button
        Button(
            onClick = { onSave(formData) },
            modifier = Modifier.fillMaxWidth(),
            enabled = formData.name.isNotBlank() && formData.type.isNotBlank()
        ) {
            Text("Save Workout")
        }
    }
} 