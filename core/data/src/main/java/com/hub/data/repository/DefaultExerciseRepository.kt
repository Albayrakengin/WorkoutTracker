package com.hub.data.repository

import com.hub.data.mapper.asEntity
import com.hub.data.mapper.asModel
import com.hub.data.mapper.asNetworkModel
import com.hub.data.model.Exercise
import com.hub.database.dao.ExerciseDao
import com.hub.network.firebase.FirebaseService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultExerciseRepository @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val firebaseService: FirebaseService
) : ExerciseRepository {

    override fun getExercises(workoutId: String): Flow<List<Exercise>> {
        return exerciseDao.getExercises(workoutId).map { entities ->
            entities.map { it.asModel() }
        }
    }

    override suspend fun getExercise(id: String): Exercise? {
        return exerciseDao.getExercise(id)?.asModel()
    }

    override suspend fun createExercise(exercise: Exercise): String {
        val id = firebaseService.createExercise(exercise.asNetworkModel())
        val exerciseWithId = exercise.copy(id = id)
        exerciseDao.insertExercise(exerciseWithId.asEntity())
        return id
    }

    override suspend fun updateExercise(exercise: Exercise) {
        firebaseService.updateExercise(exercise.asNetworkModel())
        exerciseDao.updateExercise(exercise.asEntity())
    }

    override suspend fun deleteExercise(id: String) {
        firebaseService.deleteExercise(id)
        exerciseDao.deleteExerciseById(id)
    }

    override suspend fun deleteExercisesByWorkoutId(workoutId: String) {
        exerciseDao.deleteExercisesByWorkoutId(workoutId)
    }

    override suspend fun syncExercises(workoutId: String) {
        val remoteExercises = firebaseService.getExercises(workoutId)
        remoteExercises.forEach { networkExercise ->
            exerciseDao.insertExercise(networkExercise.asEntity())
        }
    }
} 