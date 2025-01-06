package com.hub.data.model

import java.util.Date

data class WorkoutModel(
    val id: String = "",
    val userId: String = "",
    val name: String = "",
    val date: Date = Date(),
    val duration: Int = 0, // dakika cinsinden
    val exercises: List<ExerciseModel> = emptyList(),
    val notes: String = "",
    val type: WorkoutType = WorkoutType.OTHER
)

data class ExerciseModel(
    val id: String = "",
    val name: String = "",
    val sets: List<ExerciseSetModel> = emptyList(),
    val notes: String = ""
)

data class ExerciseSetModel(
    val reps: Int = 0,
    val weight: Float = 0f,
    val duration: Int = 0, // saniye cinsinden (cardio egzersizleri için)
    val distance: Float = 0f // metre cinsinden (cardio egzersizleri için)
)

enum class WorkoutType {
    STRENGTH,
    CARDIO,
    FLEXIBILITY,
    HIIT,
    OTHER
} 