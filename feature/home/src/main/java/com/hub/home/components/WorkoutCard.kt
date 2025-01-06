package com.hub.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.hub.data.model.Workout
import com.hub.designsystem.theme.WorkoutTheme
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutCard(
    workout: Workout,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = WorkoutTheme.colors.cardBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 4.dp,
            focusedElevation = 8.dp,
            hoveredElevation = 10.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 140.dp)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = workout.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        color = WorkoutTheme.colors.textColor
                    )
                    
                    if (workout.description.isNotBlank()) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = workout.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = WorkoutTheme.colors.opacityTextColor,
                            maxLines = 10
                        )
                    }
                }
                
                if (workout.lastSession.isNotBlank()) {
                    Row {
                        Text(
                            text = "Son antrenman: ",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = WorkoutTheme.colors.primaryColor,
                            maxLines = 1
                        )
                        Text(
                            text = formatDate(workout.lastSession),
                            style = MaterialTheme.typography.bodySmall,
                            color = WorkoutTheme.colors.captionColor,
                            maxLines = 1
                        )
                    }
                }
            }

            if (workout.imageUrl.isNotBlank()) {
                Spacer(modifier = Modifier.width(16.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(80.dp)
                ) {
                    AsyncImage(
                        model = workout.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

private fun formatDate(timestamp: String): String {
    return try {
        val date = Date(timestamp.toLong())
        SimpleDateFormat("dd MMM yyyy, HH:mm", Locale("tr")).format(date)
    } catch (e: Exception) {
        timestamp
    }
} 