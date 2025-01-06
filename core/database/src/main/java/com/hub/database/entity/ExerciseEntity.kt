package com.hub.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class ExerciseEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val workoutId: String,
    val name: String,
    val description: String,
    val sets: Int,
    val reps: Int,
    val weight: Float,
    val history: String = "[]", // JSON string olarak saklayacağız
    val createdAt: Long,
    val updatedAt: Long
) 