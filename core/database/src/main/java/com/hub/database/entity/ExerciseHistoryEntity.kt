package com.hub.database.entity

data class ExerciseHistoryEntity(
    val id: String,
    val exerciseId: String,
    val workoutId: String,
    val userId: String,
    val sets: Int,
    val reps: Int,
    val weight: Float,
    val date: Long,
    val updatedAt: Long
) 