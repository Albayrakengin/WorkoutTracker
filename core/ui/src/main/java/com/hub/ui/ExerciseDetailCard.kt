package com.hub.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.core.entry.entryModelOf
import com.hub.common.R
import com.hub.designsystem.theme.WorkoutTheme
import com.patrykandpatrick.vico.compose.style.currentChartStyle

@Composable
fun ExerciseDetailCard(
    exerciseName: String,
    lastWeight: Int,
    sessionWeights: List<Int>,
    sets: Int,
    reps: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = WorkoutTheme.colors.cardBackground
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = exerciseName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = WorkoutTheme.colors.textColor
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "${stringResource(R.string.weight)}: $lastWeight kg",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = WorkoutTheme.colors.textColor
            )

            Row(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (sets > 0) {
                    Text(
                        text = "${stringResource(R.string.sets)}: $sets",
                        style = MaterialTheme.typography.bodyLarge,
                        color = WorkoutTheme.colors.textColor
                    )
                }
                if (reps > 0) {
                    Text(
                        text = "${stringResource(R.string.reps)}: $reps",
                        style = MaterialTheme.typography.bodyLarge,
                        color = WorkoutTheme.colors.textColor
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = stringResource(R.string.last_five_workouts),
                style = MaterialTheme.typography.titleMedium,
                color = WorkoutTheme.colors.opacityTextColor
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            if (sessionWeights.isNotEmpty()) {
                Chart(
                    chart = lineChart(),
                    model = entryModelOf(*sessionWeights.mapIndexed { index, weight -> 
                        index.toFloat() to weight.toFloat() 
                    }.toTypedArray()),
                    startAxis = startAxis(),
                    bottomAxis = bottomAxis(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            } else {
                Text(
                    text = stringResource(R.string.no_workout_history),
                    style = MaterialTheme.typography.bodyMedium,
                    color = WorkoutTheme.colors.opacityTextColor
                )
            }
        }
    }
} 