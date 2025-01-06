package com.hub.startworkout.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ExerciseControls(
    sets: Int,
    reps: Int,
    weight: Int,
    onSetsChange: (Int) -> Unit,
    onRepsChange: (Int) -> Unit,
    onWeightChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Sets
        ControlItem(
            label = "Set",
            value = sets,
            onIncrease = { onSetsChange(sets + 1) },
            onDecrease = { if (sets > 1) onSetsChange(sets - 1) }
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Reps
        ControlItem(
            label = "Rep",
            value = reps,
            onIncrease = { onRepsChange(reps + 1) },
            onDecrease = { if (reps > 1) onRepsChange(reps - 1) }
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Weight
        ControlItem(
            label = "Weight",
            value = weight,
            suffix = "kg",
            onIncrease = { onWeightChange(weight + 5) },
            onDecrease = { if (weight > 0) onWeightChange(weight - 5) }
        )
    }
}

@Composable
private fun ControlItem(
    label: String,
    value: Int,
    suffix: String = "",
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    var tempValue by remember { mutableStateOf(value.toString()) }
    
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Decrease button
            OutlinedIconButton(
                onClick = onDecrease,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Decrease")
            }
            
            // Value display
            OutlinedCard(
                modifier = Modifier
                    .width(100.dp)
                    .clickable { showDialog = true },
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$value$suffix",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            // Increase button
            OutlinedIconButton(
                onClick = onIncrease,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Increase")
            }
        }
    }
    
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Enter $label") },
            text = {
                OutlinedTextField(
                    value = tempValue,
                    onValueChange = { tempValue = it.filter { char -> char.isDigit() } },
                    label = { Text(label) }
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        tempValue.toIntOrNull()?.let { newValue ->
                            when (label) {
                                "Set", "Rep" -> if (newValue >= 1) {
                                    if (label == "Set") onIncrease() else onDecrease()
                                }
                                "Weight" -> if (newValue >= 0) {
                                    if (newValue > value) onIncrease() else onDecrease()
                                }
                            }
                        }
                        showDialog = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
} 