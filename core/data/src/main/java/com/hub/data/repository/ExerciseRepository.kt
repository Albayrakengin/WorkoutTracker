package com.hub.data.repository

import com.hub.data.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    fun getExercises(workoutId: String): Flow<List<Exercise>>
    
    suspend fun getExercise(id: String): Exercise?
    
    suspend fun createExercise(exercise: Exercise): String
    
    suspend fun updateExercise(exercise: Exercise)
    
    suspend fun deleteExercise(id: String)
    
    suspend fun deleteExercisesByWorkoutId(workoutId: String)
    
    suspend fun syncExercises(workoutId: String)
} 