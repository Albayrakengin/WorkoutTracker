package com.hub.data.model

data class ExerciseHistory(
    val id: String = "",
    val exerciseId: String = "",
    val workoutId: String = "",
    val userId: String = "",
    val sets: Int = 0,
    val reps: Int = 0,
    val weight: Float = 0f,
    val date: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

data class Exercise(
    val id: String = "",
    val workoutId: String = "",
    val userId: String = "",
    val name: String = "",
    val description: String = "",
    val sets: Int = 0,
    val reps: Int = 0,
    val weight: Float = 0f,
    val history: List<ExerciseHistory> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) 