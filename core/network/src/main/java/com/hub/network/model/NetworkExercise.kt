package com.hub.network.model

data class NetworkExerciseHistory(
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

data class NetworkExercise(
    val id: String = "",
    val userId: String = "",
    val workoutId: String = "",
    val name: String = "",
    val description: String = "",
    val sets: Int = 0,
    val reps: Int = 0,
    val weight: Float = 0f,
    val history: List<NetworkExerciseHistory> = emptyList(),
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) 