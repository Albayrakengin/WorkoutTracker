package com.hub.data.repository

import com.hub.data.model.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun getWorkouts(): Flow<List<Workout>>
    
    suspend fun getWorkout(id: String): Workout?
    
    suspend fun createWorkout(workout: Workout): String
    
    suspend fun updateWorkout(workout: Workout)
    
    suspend fun deleteWorkout(id: String)
    
    suspend fun syncWorkouts()
} 