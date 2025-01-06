package com.hub.data.repository

import com.hub.data.mapper.asEntity
import com.hub.data.mapper.asModel
import com.hub.data.mapper.asNetworkModel
import com.hub.data.model.Workout
import com.hub.database.dao.WorkoutDao
import com.hub.network.firebase.FirebaseService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultWorkoutRepository @Inject constructor(
    private val workoutDao: WorkoutDao,
    private val firebaseService: FirebaseService
) : WorkoutRepository {

    override fun getWorkouts(): Flow<List<Workout>> {
        return workoutDao.getWorkouts().map { entities ->
            entities.map { it.asModel() }
        }
    }

    override suspend fun getWorkout(id: String): Workout? {
        return workoutDao.getWorkout(id)?.asModel()
    }

    override suspend fun createWorkout(workout: Workout): String {
        val id = firebaseService.createWorkout(workout.asNetworkModel())
        val workoutWithId = workout.copy(id = id)
        workoutDao.insertWorkout(workoutWithId.asEntity())
        return id
    }

    override suspend fun updateWorkout(workout: Workout) {
        firebaseService.updateWorkout(workout.asNetworkModel())
        workoutDao.updateWorkout(workout.asEntity())
    }

    override suspend fun deleteWorkout(id: String) {
        firebaseService.deleteWorkout(id)
        workoutDao.deleteWorkoutById(id)
    }

    override suspend fun syncWorkouts() {
        val remoteWorkouts = firebaseService.getWorkouts()
        remoteWorkouts.forEach { networkWorkout ->
            workoutDao.insertWorkout(networkWorkout.asEntity())
        }
    }
} 