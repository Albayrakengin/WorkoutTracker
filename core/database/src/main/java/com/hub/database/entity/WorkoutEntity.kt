package com.hub.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey
    val id: String,
    val userId: String,
    val name: String,
    val type: String,
    val description: String,
    val notes: String,
    val exercises: String = "[]",
    val lastSession: String,
    val imageUrl: String,
    val createdAt: Long,
    val updatedAt: Long
) 